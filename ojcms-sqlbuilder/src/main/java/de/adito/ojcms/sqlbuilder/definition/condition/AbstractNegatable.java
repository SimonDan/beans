package de.adito.ojcms.sqlbuilder.definition.condition;

/**
 * Abstract default implementation for a negatable.
 *
 * @param <NEGATABLE> the runtime type of the negatable condition
 * @author Simon Danner, 12.06.2018
 */
abstract class AbstractNegatable<NEGATABLE extends INegatable<NEGATABLE>> implements INegatable<NEGATABLE>
{
  private boolean negated = false;

  @Override
  public NEGATABLE not()
  {
    negated = true;
    //noinspection unchecked
    return (NEGATABLE) this;
  }

  @Override
  public boolean isNegated()
  {
    return negated;
  }
}
