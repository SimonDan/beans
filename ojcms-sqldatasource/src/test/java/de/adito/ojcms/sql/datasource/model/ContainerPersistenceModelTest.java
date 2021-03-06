package de.adito.ojcms.sql.datasource.model;

import de.adito.ojcms.transactions.api.*;
import de.adito.ojcms.transactions.exceptions.BeanDataNotFoundException;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the {@link ContainerPersistenceModel} with an actual database connection (in-memory).
 *
 * @author Simon Danner, 05.01.2020
 */
public class ContainerPersistenceModelTest extends AbstractDatabaseTest<ContainerPersistenceModel>
{
  @Test
  public void testInitModelInDatabase()
  {
    final ContainerPersistenceModel otherModel = new ContainerPersistenceModel(CONTAINER_ID + 2, SomeBean.class);
    otherModel.initModelInDatabase(builder);

    assertTrue(builder.hasTable(CONTAINER_ID + 2));
    assertEquals(5, builder.getColumnCount(CONTAINER_ID + 2)); //Three bean fields + id and index column -> 5
  }

  @Test
  public void testContainerSize()
  {
    assertEquals(0, model.loadSize(builder));

    addContentToContainer(0, 12, "value1", false);
    addContentToContainer(1, -1, "value2", true);
    addContentToContainer(2, 2345, "value3", true);
    assertEquals(3, model.loadSize(builder));

    storage.processRemovals(Collections.singletonMap(CONTAINER_ID, Collections.singleton(new InitialIndexKey(CONTAINER_ID, 1))));
    assertEquals(2, model.loadSize(builder));
  }

  @Test
  public void testLoadData()
  {
    final InitialIndexKey indexKeyFirstRow = new InitialIndexKey(CONTAINER_ID, 0);
    assertThrows(BeanDataNotFoundException.class, () -> model.loadDataByIndex(indexKeyFirstRow, builder));

    final List<PersistentBeanData> added = _addSomeContent();

    //Null key
    assertThrows(NullPointerException.class, () -> model.loadDataByIndex(null, builder));
    //Bad index
    assertThrows(BeanDataNotFoundException.class, () -> model.loadDataByIndex(new InitialIndexKey(CONTAINER_ID, 55), builder));
    //Bad first value identifiers
    assertFalse(model.loadDataByIdentifiers(createIdentifiers(12, "xxx"), builder).isPresent());
    //Bad second value identifiers
    assertFalse(model.loadDataByIdentifiers(createIdentifiers(666, "value1"), builder).isPresent());

    final PersistentBeanData result = model.loadDataByIndex(indexKeyFirstRow, builder);
    assertEquals(added.get(0), result);
    final PersistentBeanData result2 = model.loadDataByIdentifiers(createIdentifiers(2, "2"), builder).orElseThrow(AssertionError::new);
    assertEquals(added.get(1), result2);
  }

  @Test
  public void testLoadFullData()
  {
    final Map<Integer, PersistentBeanData> emptyResult = model.loadFullData(builder);
    assertNotNull(emptyResult);
    assertTrue(emptyResult.isEmpty());

    final List<PersistentBeanData> data = _addSomeContent();
    final Map<Integer, PersistentBeanData> result = model.loadFullData(builder);
    assertNotNull(emptyResult);
    assertEquals(3, data.size());
    assertEquals(data.get(0), result.get(0));
    assertEquals(data.get(1), result.get(1));
    assertEquals(data.get(2), result.get(2));
  }

  @Override
  protected Class<ContainerPersistenceModel> getModelType()
  {
    return ContainerPersistenceModel.class;
  }

  /**
   * Adds some content/beans to the container.
   *
   * @return the list of added bean data
   */
  private List<PersistentBeanData> _addSomeContent()
  {
    final List<PersistentBeanData> content = new ArrayList<>();
    content.add(addContentToContainer(0, 1, "1", true));
    content.add(addContentToContainer(1, 2, "2", true));
    content.add(addContentToContainer(2, 3, "3", false));

    return content;
  }
}
