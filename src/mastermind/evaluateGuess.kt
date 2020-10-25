package mastermind

data class Evaluation(val rightPosition: Int, val wrongPosition: Int)

fun evaluateGuess(secret: String, guess: String): Evaluation {
    var rp = 0
    var wp = 0
    var newSecret: String = secret
    var newGuess: String = guess
    for(i in secret.indices){
        if (secret[i] == guess[i]){
            rp +=1
            newSecret = newSecret.substring(0 until i) +
                    "G" + newSecret.substring(i+1)
            newGuess = newGuess.substring(0 until i) +
                    "G" + newGuess.substring(i+1)
            while(guess[i] in newSecret
                    && guess[i] !in newGuess.substring(i+1)
                    && guess[i] in newGuess.substring(0 until i)){
                var ind = 0
                for (j in newGuess.indices){
                    if( newGuess[j] == guess[i]){
                        ind = j
                        break
                    }
                }
                var ind2 = 0
                for (j in newSecret.indices){
                    if( newSecret[j] == guess[i]){
                        ind2 = j
                        break
                    }
                }
                newGuess = newGuess.substring(0 until ind) +
                        "G" + newGuess.substring(ind+1)
                newSecret = newSecret.substring(0 until ind2) +
                        "G" + newSecret.substring(ind2+1)
                wp+=1
            }
        }
        else if (guess[i] in newSecret ){
            if (guess[i] in newSecret.substring(0 until i)) {
                var ind = 0
                for (j in newSecret.indices){
                    if( newSecret[j] == guess[i]){
                        ind = j
                        break
                    }
                }
                newSecret = newSecret.substring(0 until ind) +
                        "G" + newSecret.substring(ind+1)
                newGuess = newGuess.substring(0 until i) +
                        "G" + newGuess.substring(i+1)
                wp+=1
            }
            else if(guess[i] in newSecret.substring(i+1)
                    && guess[i] !in newGuess.substring(i+1)
                    && guess[i] !in newGuess.substring(0 until i)){
                wp+=1
                newGuess = newGuess.substring(0 until i) +
                        "G" + newGuess.substring(i+1)

            }
            else if(guess[i] in newSecret.substring(i+1)
                    && guess[i] !in newGuess.substring(i+1)
                    && guess[i] in newGuess.substring(0 until i)){
                wp+=1
                var ind = 0
                for (j in newSecret.indices){
                    if( newSecret[j] == guess[i]){
                        ind = j
                        break
                    }
                }
                newGuess = newGuess.substring(0 until i) +
                        "G" + newGuess.substring(i+1)
                newSecret = newSecret.substring(0 until ind) +
                        "G" + newSecret.substring(ind+1)
                while(guess[i] in newSecret
                        && guess[i] in newGuess.substring(0 until i)){
                    var ind = 0
                    for (j in newGuess.indices){
                        if( newGuess[j] == guess[i]){
                            ind = j
                            break
                        }
                    }
                    var ind2 = 0
                    for (j in newSecret.indices){
                        if( newSecret[j] == guess[i]){
                            ind2 = j
                            break
                        }
                    }
                    newGuess = newGuess.substring(0 until ind) +
                            "G" + newGuess.substring(ind+1)
                    newSecret = newSecret.substring(0 until ind2) +
                            "G" + newSecret.substring(ind2+1)
                    wp+=1
                }
            }
        }
    }
    return Evaluation(rp,wp)
}
