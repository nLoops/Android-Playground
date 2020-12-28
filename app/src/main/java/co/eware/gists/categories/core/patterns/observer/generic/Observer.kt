package co.eware.gists.categories.core.patterns.observer.generic

/**
 * Created by Ahmed Ibrahim on 24,December,2020
 *
 * Observer
 *
 * @param <S> Observable
 * @param <O> Observer
 * @param <A> Action
 */
interface Observer<S : Observable<S, O, A>, O : Observer<S, O, A>, A> {

    fun update(subject: S, argument: A)
}