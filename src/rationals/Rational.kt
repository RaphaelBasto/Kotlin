package rationals

import java.math.BigInteger

data class Rational(val numerator: BigInteger, val denominator: BigInteger): Comparable<Rational>{
    private val n: BigInteger
    private val d: BigInteger
    init {
        val gcd = numerator.gcd(denominator)
        val sign = denominator.signum().toBigInteger()
        n = (numerator * sign)/gcd
        d = (denominator * sign)/gcd

    }


    operator fun plus(rational: Rational): Rational = Rational (this.n*(rational.d) + rational.n*(this.d),
        this.d * rational.d)

    operator fun minus(rational: Rational): Rational = Rational (this.n*(rational.d) - rational.n*(this.d),
            this.d * rational.d)
    operator fun times(rational: Rational): Rational =
            Rational ((this.n)*  (rational.n), (this.d) * (rational.d))

    operator fun div(rational: Rational): Rational =
            Rational ((this.n)*  (rational.d), (this.d) * (rational.n))

    operator fun unaryMinus(): Rational = Rational(this.n * (- BigInteger.ONE),
            this.d)

    override operator fun compareTo(other: Rational): Int {
        if (other.n < BigInteger.ZERO && this.n > BigInteger.ZERO){
            return (this.n*(other.d) + other.n*(this.d)).toInt()
        }
        return (this.n*(other.d) - other.n*(this.d)).toInt()

    }
//    operator fun rangeTo(rational: Rational): Any {
//
//    }

    fun equals(rational: Rational): Boolean {
        val gcd = this.d.gcd(rational.d)
        val n1 = this.n * (gcd/this.d)
        val n2 = rational.n * (gcd/rational.d)
        println(n1)
        println(n2)
        return n1 == n2
    }
    override fun toString(): String{
        if(this.d == BigInteger.ONE){
            return "${this.n}"
        }
        return "${this.n}/${this.d}"
    }

    operator fun rangeTo(end: Rational): ClosedRange<Rational> {
        return object : ClosedRange<Rational> {
            override val endInclusive: Rational = end
            override val start: Rational = this@Rational
        }
    }


}



fun main() {
    val half = 1 divBy 2
    val third = 1 divBy 3

    val sum: Rational = half + third
    println(5 divBy 6 == sum)

    val difference: Rational = half - third
    println(1 divBy 6 == difference)

    val product: Rational = half * third
    println(1 divBy 6 == product)

    val quotient: Rational = half / third
    println(3 divBy 2 == quotient)

    val negation: Rational = -half
    println(-1 divBy 2 == negation)

    println((2 divBy 1).toString() == "2")
    println((-2 divBy 4).toString() == "-1/2")
    println("117/1098".toRational().toString() == "13/122")

    val twoThirds = 2 divBy 3
    println(half < twoThirds)

    println(half in third..twoThirds)

    println(2000000000L divBy 4000000000L == 1 divBy 2)

    println("912016490186296920119201192141970416029".toBigInteger() divBy
            "1824032980372593840238402384283940832058".toBigInteger() == 1 divBy 2)
}

infix fun Int.divBy(i: Int): Rational {
    val gcd = this.toBigInteger().gcd(i.toBigInteger())
    return Rational(this.toBigInteger() / gcd, i.toBigInteger() / gcd)
}
infix fun Int.divBy(i: Long): Rational {
    val gcd = this.toBigInteger().gcd(i.toBigInteger())
    return Rational(this.toBigInteger() / gcd, i.toBigInteger() / gcd)
}
infix fun Int.divBy(i: BigInteger): Rational {
    val gcd = this.toBigInteger().gcd(i)
    return Rational(this.toBigInteger() / gcd, i / gcd)
}
infix fun Long.divBy(i: Int): Rational {
    val gcd = this.toBigInteger().gcd(i.toBigInteger())
    return Rational(this.toBigInteger() / gcd, i.toBigInteger() / gcd)
}
infix fun Long.divBy(i: Long): Rational {
    val gcd = this.toBigInteger().gcd(i.toBigInteger())
    return Rational(this.toBigInteger() / gcd, i.toBigInteger() / gcd)
}
infix fun Long.divBy(i: BigInteger): Rational {
    val gcd = this.toBigInteger().gcd(i)
    return Rational(this.toBigInteger() / gcd, i / gcd)
}
infix fun BigInteger.divBy(i: Int): Rational {
    val gcd = this.gcd(i.toBigInteger())
    return Rational(this / gcd, i.toBigInteger() / gcd)
}
infix fun BigInteger.divBy(i: Long): Rational {
    val gcd = this.gcd(i.toBigInteger())
    return Rational(this / gcd, i.toBigInteger() / gcd)
}
infix fun BigInteger.divBy(i: BigInteger): Rational {
    val gcd = this.gcd(i)
    return Rational(this / gcd, i / gcd)
}
fun String.toRational(): Rational {
    var n = this.substringBefore('/').toBigInteger()
    var d = this.substringAfter('/').toBigInteger()
    return if (this.contains('/')){
        Rational(n, d)
    } else {
        Rational(this.toBigInteger(), BigInteger.ONE)
    }
}
