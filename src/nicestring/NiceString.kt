package nicestring

fun String.isNice(): Boolean {

    val condition1 = setOf("ba", "be", "bu").none { this.contains(it) }

    val condition2 = count { it in "aeiou" } >=3

    val condition3 = zipWithNext().any { it.first == it.second }

    return listOf(condition1, condition2, condition3).count { it } >= 2
}