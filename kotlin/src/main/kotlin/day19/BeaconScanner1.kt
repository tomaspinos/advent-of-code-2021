package day19

import Common

fun main() {
    println(BeaconScanner1().go(Common.readInput("/day19/input.txt")))
}

class BeaconScanner1 {

    var beaconMatchTreshold: Int = 12

    val rotationXY = Rotation(Axis.x, Axis.y, Axis.z)
    val rotationXZ = Rotation(Axis.x, Axis.z, Axis.y)
    val rotationYZ = Rotation(Axis.y, Axis.z, Axis.x)

    fun go(input: List<String>): Int {
        val reports = readInput(input)

        val beacons = HashSet<Point>()

        val report0 = reports.first()

        beacons.addAll(report0.points)

        for (i in 1 until reports.size) {
            println("*** $i ***")

            val report = reports[i]

            val commonDistances = report0.matchCommonDistances(report)
            println("  ${commonDistances.size}")
            if (commonDistances.size < beaconMatchTreshold) {
                continue
            }

            val pairs = report0.getPointPairsByDistances(commonDistances)
            val vectors = pairs.flatMap { pair -> pair.vectors }

            val rotations = rotations(report).distinct().toList()
            var bestRotation: ScannerReport? = null
            var bestRotationIntersection = Int.MIN_VALUE
            for (rotation in rotations) {
                val rotationPairs = rotation.getPointPairsByDistances(commonDistances)
                val rotationVectors = rotationPairs.flatMap { pair -> pair.vectors }

                val vectorIntersection = vectors intersect rotationVectors
                if (vectorIntersection.size > bestRotationIntersection) {
                    bestRotationIntersection = vectorIntersection.size
                    bestRotation = rotation
                }
            }

            println("  $bestRotationIntersection")

            if (bestRotation == null) {
                throw IllegalStateException("No fitting rotation")
            }

            beacons.addAll(bestRotation.points)
        }

        return beacons.size
    }

    fun readInput(input: List<String>): List<ScannerReport> {
        val reports = ArrayList<ScannerReport>()
        val lines = ArrayList(input)
        while (lines.isNotEmpty()) {
            reports.add(readReport(reports.size, lines))
            if (lines.isNotEmpty() && lines.first().isBlank()) {
                lines.removeFirst()
            }
        }
        return reports
    }

    private fun rotations(prism0: ScannerReport): Sequence<ScannerReport> {
        return sequenceOf(prism0)
            .flatMap(rotationXY::rotate)
            .flatMap(rotationXZ::rotate)
            .flatMap(rotationYZ::rotate)
    }

    private fun readReport(index: Int, lines: MutableList<String>): ScannerReport {
        val firstLine = lines.removeFirst()
        if (!firstLine.startsWith("---")) {
            throw IllegalStateException("Unexpected line: $firstLine")
        }

        val points = ArrayList<Point>()
        while (lines.isNotEmpty() && lines.first().isNotBlank()) {
            val line = lines.removeFirst()
            val xyz = line.split(',').map { s -> s.toInt() }
            val point = Point(xyz[0], xyz[1], xyz[2])
            points.add(point)
        }

        return ScannerReport(index, points)
    }

    data class Point(val x: Int, val y: Int, val z: Int) {

        fun get(axis: Axis): Int {
            return when (axis) {
                Axis.x -> x
                Axis.y -> y
                Axis.z -> z
            }
        }
    }

    data class ScannerReport(val scanner: Int, val points: List<Point>) {

        val distances: Map<Int, List<PointPair>>

        init {
            val pointPairs = HashSet<PointPair>()
            for (i in 0 until points.size) {
                for (j in 0 until points.size) {
                    if (i != j) {
                        pointPairs.add(PointPair(points[i], points[j]))
                    }
                }
            }
            distances = pointPairs.groupBy { pair -> pair.distance }
        }

        fun matchCommonDistances(other: ScannerReport): Set<Int> {
            return distances.keys.intersect(other.distances.keys)
        }

        fun getPointPairsByDistances(someDistances: Set<Int>): Set<PointPair> {
            return distances
                .filter { entry -> someDistances.contains(entry.key) }
                .values
                .flatten()
                .toSet()
        }
    }

    data class PointPair(val a: Point, val b: Point) {

        val distance = distance(a, b)

        val vectors = listOf(Point(a.x - b.x, a.y - b.y, a.z - b.z), Point(b.x - a.x, b.y - a.y, b.z - a.z))

        override fun hashCode(): Int {
            return a.hashCode() + b.hashCode()
        }

        override fun equals(other: Any?): Boolean {
            if (other !is PointPair) {
                return false
            }
            return (a == other.a && b == other.b) || (a == other.b && b == other.a)
        }
    }

    enum class Axis {
        x, y, z

    }

    data class Rotation(val axis1: Axis, val axis2: Axis, val staticAxis: Axis) {

        fun rotate(point: Point): Point {
            val coordinate1 = point.get(axis1)
            val coordinate2 = point.get(axis2)
            val staticCoordinate = point.get(staticAxis)
            val newCoordinate1 = -coordinate2
            val newCoordinate2 = coordinate1
            return point(
                mapOf(
                    Pair(axis1, newCoordinate1),
                    Pair(axis2, newCoordinate2),
                    Pair(staticAxis, staticCoordinate)
                )
            )
        }

        fun rotate(report: ScannerReport): List<ScannerReport> {
            val report1 = ScannerReport(report.scanner, report.points.map(this::rotate))
            val report2 = ScannerReport(report.scanner, report1.points.map(this::rotate))
            val report3 = ScannerReport(report.scanner, report2.points.map(this::rotate))
            return listOf(report, report1, report2, report3)
        }

        private fun point(coordinates: Map<Axis, Int>): Point {
            return Point(coordinates[Axis.x]!!, coordinates[Axis.y]!!, coordinates[Axis.z]!!)
        }
    }

    companion object {

        fun distance(a: Point, b: Point): Int {
            val xx = (a.x - b.x) * (a.x - b.x)
            val yy = (a.y - b.y) * (a.y - b.y)
            val zz = (a.z - b.z) * (a.z - b.z)
            return xx + yy + zz
        }
    }
}