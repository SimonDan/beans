package de.adito.ojcms.beans;

import de.adito.ojcms.beans.annotations.internal.EncapsulatedData;
import de.adito.ojcms.beans.datasource.IDataSource;
import de.adito.ojcms.beans.literals.fields.IField;
import de.adito.ojcms.beans.reactive.events.*;
import io.reactivex.Observable;

import java.util.Objects;

import static de.adito.ojcms.beans.BeanInternalEvents.requestEncapsulatedData;

/**
 * Provides {@link Observable} instances for bean events.
 * A type implementing this interface must hold an {@link IEncapsulatedData} data core which acts as an internal {@link IEventPublisher}.
 *
 * @param <ELEMENT>      the type of the elements in the encapsulated data core
 * @param <ENCAPSULATED> the type of the encapsulated data core held by the type implementing this interface
 * @author Simon Danner, 07.12.2018
 */
@EncapsulatedData
interface IBeanEventPublisher<ELEMENT, DATASOURCE extends IDataSource, ENCAPSULATED extends IEncapsulatedData<ELEMENT, DATASOURCE>>
    extends IEncapsulatedDataHolder<ELEMENT, DATASOURCE, ENCAPSULATED>
{
  /**
   * An {@link Observable} to observe value changes of bean values.
   *
   * @return an observable that publishes {@link BeanValueChange} events
   */
  default Observable<BeanValueChange<?>> observeValues()
  {
    return requestEncapsulatedData(this).observeByType(BeanValueChange.class) //
        .map(pChange -> (BeanValueChange<?>) pChange);
  }

  /**
   * An {@link Observable} to observe value changes of bean values from a certain field.
   *
   * @return an observable that publishes {@link BeanValueChange} events of the specific field
   */
  default <VALUE> Observable<BeanValueChange<VALUE>> observeFieldValue(IField<VALUE> pField)
  {
    Objects.requireNonNull(pField);
    //noinspection unchecked
    return observeValues() //
        .filter(pChange -> pChange.getField() == pField) //
        .map(pChange -> (BeanValueChange<VALUE>) pChange);
  }

  /**
   * An {@link Observable} to observe field addition events.
   *
   * @return an observable that publishes {@link BeanFieldAddition} events
   */
  default Observable<BeanFieldAddition<?>> observeFieldAdditions()
  {
    return requestEncapsulatedData(this).observeByType(BeanFieldAddition.class) //
        .map(pChange -> (BeanFieldAddition<?>) pChange);
  }

  /**
   * An {@link Observable} to observe field removal events.
   *
   * @return an observable that publishes {@link BeanFieldRemoval} events
   */
  default Observable<BeanFieldRemoval<?>> observeFieldRemovals()
  {
    return requestEncapsulatedData(this).observeByType(BeanFieldRemoval.class) //
        .map(pChange -> (BeanFieldRemoval<?>) pChange);
  }
}
