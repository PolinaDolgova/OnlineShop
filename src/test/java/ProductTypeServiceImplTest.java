import org.junit.Test;
import ru.nc.shop.model.Category;
import ru.nc.shop.model.ProductType;
import ru.nc.shop.service.ProductTypeServiceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class ProductTypeServiceImplTest {
    private final ProductTypeServiceImpl productTypeServiceImpl = new ProductTypeServiceImpl();
    private final Map<String, ProductType> expectObjectsOfProductsType = new HashMap<>();
    private final Map<String, ProductType> testObjectsOfProductsType = new HashMap<>();
    private List<Category> testDataOfCategory = new ArrayList<>();
    private List<Long> testValuesOfZoneId = new ArrayList<>();

    private List<Category> testValuesForChangeCategoryOfProductType = new ArrayList<>();

    @org.junit.Before
    public void setUpChangeOfProductType() {
        testValuesForChangeCategoryOfProductType.add(new Category(4L));
        testValuesForChangeCategoryOfProductType.add(new Category(9L));
        testValuesOfZoneId.add(0,1L);
        testValuesOfZoneId.add(1,1L);

        testDataOfCategory.add(new Category(4L));
        expectObjectsOfProductsType.put("test1", new ProductType(testDataOfCategory, 1L));
        testObjectsOfProductsType.put("test1", new ProductType(testDataOfCategory,3L));
        testObjectsOfProductsType.put("test2", new ProductType(testDataOfCategory,1L));

        testDataOfCategory.add(new Category(9L));
        expectObjectsOfProductsType.put("test2", new ProductType(testDataOfCategory, 1L));
    }

    @org.junit.After
    public void tearDownChangeOfProductType() {
        expectObjectsOfProductsType.clear();
        testObjectsOfProductsType.clear();
        testValuesForChangeCategoryOfProductType.clear();
        testDataOfCategory.clear();
        testValuesOfZoneId.clear();
    }

    @Test
    public void testChangeCategoryOfProductType() {
        int indexOfListTestParametersForMethod = 0;
        for (Map.Entry<String, ProductType> testObject : testObjectsOfProductsType.entrySet()) {
            for (Map.Entry<String, ProductType> expectedObject : expectObjectsOfProductsType.entrySet()){
                ProductType testData = testObject.getValue();

                if (expectedObject.getKey().equals(testObject.getKey())){
                    ProductType expected = expectedObject.getValue();

                    ProductType actual = productTypeServiceImpl.changeCategoryOfProductType(testData,
                            testValuesForChangeCategoryOfProductType.get(indexOfListTestParametersForMethod));

                    assertEquals(expected.getCategories(), actual.getCategories());
                }
            }
            indexOfListTestParametersForMethod ++;
        }
    }

    @Test
    public void testChangePlacementOfProductType(){
        int indexOfListTestParametersForMethod = 0;
        for (Map.Entry<String, ProductType> testObject : testObjectsOfProductsType.entrySet()) {
            for (Map.Entry<String, ProductType> expectedObject : expectObjectsOfProductsType.entrySet()){
                ProductType testData = testObject.getValue();

                if (expectedObject.getKey().equals(testObject.getKey())){
                    ProductType expected = expectedObject.getValue();

                    ProductType actual = productTypeServiceImpl.changePlacementOfProductType(testData,
                            testValuesOfZoneId.get(indexOfListTestParametersForMethod));

                    assertEquals(expected.getZoneId(), actual.getZoneId());
                }
            }
            indexOfListTestParametersForMethod ++;
        }
    }
}
