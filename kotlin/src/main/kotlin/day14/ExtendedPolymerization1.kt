package day14

import Common

fun main() {
    println(ExtendedPolymerization1().go(Common.readInput("/day14/input.txt")))
}

class ExtendedPolymerization1 {

    fun go(input: List<String>): Int {
        val templateAndPairInsertionRules = readInput(input)

        val template = templateAndPairInsertionRules.template
        val rules = templateAndPairInsertionRules.rules

        return template.pairs()
            .map { pair -> applyRules(pair, rules, 1) }
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

    private fun applyRules(pair: String, rules: PairInsertionRules, step: Int): CharacterStats {
        if (step > 10) {
            return CharacterStats(emptyMap())
        }

        val newElement = rules.getNewElement(pair)
        return if (newElement != null) {
            CharacterStats(mapOf(Pair(newElement, 1)))
                .merge(applyRules("${pair[0]}$newElement", rules, step + 1))
                .merge(applyRules("$newElement${pair[1]}", rules, step + 1))
        } else {
            CharacterStats(emptyMap())
        }
    }

    data class Template(val template: String) {

        fun pairs(): List<String> {
            val pairs = ArrayList<String>()
            for (i in 0..template.length - 2) {
                pairs.add(template.substring(i, i + 2))
            }
            return pairs
        }

        fun stats(): CharacterStats {
            val map = HashMap<Char, Int>()
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

    data class CharacterStats(val map: Map<Char, Int>) {

        fun merge(other: CharacterStats): CharacterStats {
            val resultingMap = HashMap(map)
            other.map.entries.forEach { entry ->
                resultingMap.merge(entry.key, entry.value) { count1, count2 -> count1 + count2 }
            }
            return CharacterStats(resultingMap)
        }

        fun result(): Int {
            val min = map.values.minOrNull()!!
            val max = map.values.maxOrNull()!!
            return max - min
        }
    }
}