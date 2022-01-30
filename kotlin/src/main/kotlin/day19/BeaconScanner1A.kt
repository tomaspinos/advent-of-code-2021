package day19

import Common

fun main() {
    println(BeaconScanner1A().go(Common.readInput("/day19/input.txt")))
}

class BeaconScanner1A {

    var beaconMatchTreshold: Int = 12

    val rotationXY = Rotation(Axis.x, Axis.y, Axis.z)
    val rotationXZ = Rotation(Axis.x, Axis.z, Axis.y)
    val rotationYZ = Rotation(Axis.y, Axis.z, Axis.x)

    fun go(input: List<String>): Int {
        val reports = readInput(input)

        val prisms = reports.map { report -> report.minimalPrism() }

        val prism0 = prisms.first()

        val synchronizedPrisms = ArrayList<ScannerReport>()
        synchronizedPrisms.add(prism0)

        for (i in 1 until prisms.size) {
            val prism = prisms[i]
            synchronizedPrisms.add(match(prism0, prism))
        }

        return synchronizedPrisms.flatMap { prism -> prism.points }.toSet().size
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

    private fun match(prism0: ScannerReport, prism1: ScannerReport): ScannerReport {
        println("match")
        println(prism0)
        val rotations = rotations(prism1)
        for (rotation in rotations) {
            if (prism0.matchCommonBeacons(rotation) >= beaconMatchTreshold) {
                return rotation
            }
        }
        throw IllegalStateException("Cannot match prism: $prism1")
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

        fun apply(delta: Point): Point {
            return Point(x + delta.x, y + delta.y, z + delta.z)
        }

        fun get(axis: Axis): Int {
            return when (axis) {
                Axis.x -> x
                Axis.y -> y
                Axis.z -> z
            }
        }
    }

    data class ScannerReport(val scanner: Int, val points: List<Point>) {

        fun minimalPrism(): ScannerReport {
            val minX = points.minOf { point -> point.x }
            val minY = points.minOf { point -> point.y }
            val minZ = points.minOf { point -> point.z }
            val delta = Point(0 - minX, 0 - minY, 0 - minZ)
            val transformedPoints = points.map { point -> point.apply(delta) }
            return ScannerReport(scanner, transformedPoints)
        }

        fun matchCommonBeacons(other: ScannerReport): Int {
            println(other)
            return points.intersect(other.points.toSet()).size
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
            val report1 = ScannerReport(report.scanner, report.points.map(this::rotate)).minimalPrism()
            val report2 = ScannerReport(report.scanner, report1.points.map(this::rotate)).minimalPrism()
            val report3 = ScannerReport(report.scanner, report2.points.map(this::rotate)).minimalPrism()
            return listOf(report, report1, report2, report3)
        }

        private fun point(coordinates: Map<Axis, Int>): Point {
            return Point(coordinates[Axis.x]!!, coordinates[Axis.y]!!, coordinates[Axis.z]!!)
        }
    }
}