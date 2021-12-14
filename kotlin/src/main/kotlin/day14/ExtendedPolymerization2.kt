package day14

import Common

fun main() {
    println(ExtendedPolymerization2().go(Common.readInput("/day14/input.txt")))
}

class ExtendedPolymerization2 {

    fun go(input: List<String>): Long {
        val templateAndPairInsertionRules = readInput(input)

        val template = templateAndPairInsertionRules.template
        val rules = templateAndPairInsertionRules.rules

        val resultCache = HashMap<ResultCacheKey, CharacterStats>()
        return template.pairs()
            .map { pair -> applyRules(pair, rules, 1, resultCache) }
            .reduce { overallStats, pairStats -> overallStats.merge(pairStats) }
            .merge(template.stats())
            .result()
    }

    private fun readInput(input: List<String>): TemplateAndPairInsertionRules {
        val iterator = input.iterator()

        val template = iterator.next()

        iterator.next()

        val ruleMap = HashMap<String, Char>()
        while (iterator.hasNext()) {
            val rule = iterator.next()
            val split = rule.split(" -> ")
            ruleMap[split[0]] = split[1][0]
        }

        return TemplateAndPairInsertionRules(Template(template), PairInsertionRules(ruleMap))
    }

    private fun applyRules(pair: String, rules: PairInsertionRules, step: Int, cache: HashMap<ResultCacheKey, CharacterStats>): CharacterStats {
        if (step > 40) {
            return CharacterStats(emptyMap())
        }

        val maybeResult = cache[ResultCacheKey(pair, step)]
        return if (maybeResult != null) {
            maybeResult
        } else {
            val newElement = rules.getNewElement(pair)
            val stats = if (newElement != null) {
                CharacterStats(mapOf(Pair(newElement, 1)))
                    .merge(applyRules("${pair[0]}$newElement", rules, step + 1, cache))
                    .merge(applyRules("$newElement${pair[1]}", rules, step + 1, cache))
            } else {
                CharacterStats(emptyMap())
            }
            cache[ResultCacheKey(pair, step)] = stats
            stats
        }
    }

    data class Template(val template: String) {

        fun pairs(): List<String> {
            return template.windowed(2, 1)
        }

        fun stats(): CharacterStats {
            val map = HashMap<Char, Long>()
            template.toCharArray().forEach { char ->
                map.merge(char, 1) { count1, count2 -> count1 + count2 }
            }
            return CharacterStats(map)
        }
    }

    data class PairInsertionRules(val map: Map<String, Char>) {

        fun getNewElement(pair: String): Char? {
            return map[pair]
        }
    }

    data class TemplateAndPairInsertionRules(val template: Template, val rules: PairInsertionRules)

    data class CharacterStats(val map: Map<Char, Long>) {

        fun merge(other: CharacterStats): CharacterStats {
            val resultingMap = HashMap(map)
            other.map.entries.forEach { entry ->
                resultingMap.merge(entry.key, entry.value) { count1, count2 -> count1 + count2 }
            }
            return CharacterStats(resultingMap)
        }

        fun result(): Long {
            val min = map.values.minOrNull()!!
            val max = map.values.maxOrNull()!!
            return max - min
        }
    }

    data class ResultCacheKey(val pair: String, val step: Int)
}