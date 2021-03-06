package de.adito.ojcms.beans.literals.fields.types;

import de.adito.ojcms.beans.annotations.internal.TypeDefaultField;
import de.adito.ojcms.beans.literals.fields.serialization.ISerializableField;
import de.adito.ojcms.beans.literals.fields.util.CustomFieldCopy;
import de.adito.ojcms.beans.util.*;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;
import java.time.*;
import java.time.format.*;
import java.util.*;

/**
 * A bean field that holds a timestamp (as {@link Instant}).
 * Provides converters for {@link Date} and {@link Long}.
 *
 * @author Simon Danner, 23.08.2016
 */
@TypeDefaultField(types = {Instant.class, Date.class})
public class TimestampField extends AbstractField<Instant> implements ISerializableField<Instant, Long>
{
  protected TimestampField(@NotNull String pName, Collection<Annotation> pAnnotations, boolean pIsOptional, boolean pIsPrivate)
  {
    super(Instant.class, pName, pAnnotations, pIsOptional, pIsPrivate);
    registerConverter(Date.class, Date::toInstant, Date::from);
    registerConverter(Long.class, Instant::ofEpochMilli, Instant::toEpochMilli);
  }

  @Override
  public String display(Instant pValue, IClientInfo pClientInfo)
  {
    final ZonedDateTime date = ZonedDateTime.ofInstant(pValue, pClientInfo.getZoneId());
    return DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.MEDIUM) //
        .withLocale(pClientInfo.getLocale()) //
        .format(date);
  }

  @Override
  public Instant copyValue(Instant pInstant, ECopyMode pMode, CustomFieldCopy<?>... pCustomFieldCopies)
  {
    return Instant.ofEpochMilli(pInstant.toEpochMilli());
  }

  @Override
  public Long toPersistent(Instant pValue)
  {
    return pValue == null ? null : pValue.toEpochMilli();
  }

  @Override
  public Class<Long> getSerialValueType()
  {
    return Long.class;
  }

  @Override
  public Instant fromPersistent(Long pSerialValue)
  {
    return pSerialValue == null ? null : Instant.ofEpochMilli(pSerialValue);
  }
}
