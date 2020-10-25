package taxipark

import org.w3c.dom.ranges.Range

/*
 * Task #1. Find all the drivers who performed no trips.
 */
fun TaxiPark.findFakeDrivers(): Set<Driver> =
        allDrivers.minus(trips.map { it.driver }.toSet())

/*
 * Task #2. Find all the clients who completed at least the given number of trips.
 */
fun TaxiPark.findFaithfulPassengers(minTrips: Int): Set<Passenger> =

    allPassengers
            .filter { p->
                trips.count{ p in it.passengers} >= minTrips
            }
            .toSet()


/*
 * Task #3. Find all the passengers, who were taken by a given driver more than once.
 */
fun TaxiPark.findFrequentPassengers(driver: Driver): Set<Passenger> =
    allPassengers
            .filter { p ->
                trips.count { it.driver == driver && p in it.passengers } > 1
            }
            .toSet()

/*
 * Task #4. Find the passengers who had a discount for majority of their trips.
 */
fun TaxiPark.findSmartPassengers(): Set<Passenger> {

    val mapa: HashMap<Passenger, Int> = HashMap()
    for (passenger in allPassengers){
        mapa[passenger] = 0
    }
    for (i in trips) {
        if (i.discount!=null && i.discount > 0.0 ){
            i.passengers.forEach {
                val valor = mapa[it]
                if (valor != null) { mapa[it] = valor + 1 }
            }
        }
        else if (i.discount==null || i.discount == 0.0 ){
            i.passengers.forEach {
                val valor = mapa[it]
                if (valor != null) { mapa[it] = valor - 1 }
            }
        }
    }
    return mapa.filter { it.value>0  }.keys
}
/*
 * Task #5. Find the most frequent trip duration among minute periods 0..9, 10..19, 20..29, and so on.
 * Return any period if many are the most frequent, return `null` if there're no trips.
 */
fun TaxiPark.findTheMostFrequentTripDurationPeriod(): IntRange? {

     if (trips.isEmpty()){
         return null
     }
    val ran = trips.toTypedArray().groupBy { (it.duration/10).toInt()  }.maxBy { it.value.size }!!.key

    return (ran*10)..((ran*10)+9)
}

/*
 * Task #6.
 * Check whether 20% of the drivers contribute 80% of the income.
 */
fun TaxiPark.checkParetoPrinciple(): Boolean {
    if (trips.isEmpty()) return false

    val totalIncome = trips.sumByDouble { it.cost }
    val sortedDriversIncome: List<Double> = trips
            .groupBy{it.driver}
            .map { (_, tripsByDriver) -> tripsByDriver.sumByDouble { trip -> trip.cost } }
            .sortedDescending()

    val numberOfTopDrivers = (0.2 * allDrivers.size).toInt()
    val incomeByTopDrivers = sortedDriversIncome
            .take(numberOfTopDrivers)
            .sum()

    return incomeByTopDrivers >= 0.8 * totalIncome
}