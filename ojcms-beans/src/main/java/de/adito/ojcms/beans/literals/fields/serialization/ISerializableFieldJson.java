package de.adito.ojcms.beans.literals.fields.serialization;

import com.google.gson.Gson;

/**
 * Serializable bean field using JSON to persist field values.
 *
 * @param <VALUE> the value type of the bean field
 * @author Simon Danner, 07.12.2018
 */
public interface ISerializableFieldJson<VALUE> extends ISerializableField<VALUE, String>
{
  @Override
  default String toPersistent(VALUE pValue)
  {
    return new Gson().toJson(pValue);
  }

  @Override
  default VALUE fromPersistent(String pSerialString)
  {
    return new Gson().fromJson(pSerialString, getDataType());
  }

  @Override
  default Class<String> getSerialValueType()
  {
    return String.class;
  }
}
