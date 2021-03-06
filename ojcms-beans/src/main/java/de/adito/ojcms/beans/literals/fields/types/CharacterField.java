package de.adito.ojcms.beans.literals.fields.types;

import de.adito.ojcms.beans.annotations.NeverNull;
import de.adito.ojcms.beans.annotations.internal.TypeDefaultField;
import de.adito.ojcms.beans.literals.fields.serialization.IAutoSerializableField;
import de.adito.ojcms.beans.literals.fields.util.CustomFieldCopy;
import de.adito.ojcms.beans.util.ECopyMode;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;
import java.util.Collection;

/**
 * A bean field that holds a character.
 *
 * @author Simon Danner, 07.09.2017
 */
@NeverNull
@TypeDefaultField(types = Character.class)
public class CharacterField extends AbstractField<Character> implements IAutoSerializableField<Character>
{
  protected CharacterField(@NotNull String pName, Collection<Annotation> pAnnotations, boolean pIsOptional, boolean pIsPrivate)
  {
    super(Character.class, pName, pAnnotations, pIsOptional, pIsPrivate);
  }

  @Override
  public Character getInitialValue()
  {
    return '\u0000';
  }

  @Override
  public Character copyValue(Character pCharacter, ECopyMode pMode, CustomFieldCopy<?>... pCustomFieldCopies)
  {
    return pCharacter;
  }
}
