package co.eware.gists.categories.core.patterns.observer.generic

import java.util.concurrent.CopyOnWriteArrayList

/**
 * Created by Ahmed Ibrahim on 24,December,2020
 *
 * Observable
 *
 * @param <S> Subject
 * @param <O> Observer
 * @param <A> Argument type
 */
abstract class Observable<S : Observable<S, O, A>, O : Observer<S, O, A>, A> {

    var observers: MutableList<O> = CopyOnWriteArrayList()

    fun addObserver(observer: O) = observers.add(observer)

    fun removeObserver(observer: O) = observers.remove(observer)

    /**
     * Notify observers with changes
     */
    @Suppress("UNCHECKED_CAST")
    fun notifyObservers(argument: A) {
        observers.forEach { ob ->
            ob.update(this as S, argument)
        }
    }
}