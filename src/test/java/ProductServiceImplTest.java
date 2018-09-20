import static org.junit.Assert.assertEquals;
import org.junit.Test;
import ru.nc.shop.model.Product;
import ru.nc.shop.model.ProductType;
import ru.nc.shop.service.ProductServiceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ProductServiceImplTest {
    private final ProductServiceImpl productServiceImpl = new ProductServiceImpl();
    private final Map<String, Product> expectObjectsOfProducts = new HashMap<>();
    private final Map<String, Product> testObjectsOfProducts = new HashMap<>();

    private List<Long> testValuesSalesStatusId = new ArrayList<>();
    private List<Long> testValuesLogisticsStatusId = new ArrayList<>();

    @org.junit.Before
    public void setUpChangeStatusOfProductData() {
        expectObjectsOfProducts.put("test1", new Product(1L, new ProductType(), 3L, 4L));
        expectObjectsOfProducts.put("test2", new Product(2L, new ProductType(), 1L, 4L));

        testObjectsOfProducts.put("test1", new Product(1L, new ProductType(), 1L, 7L));
        testObjectsOfProducts.put("test2", new Product(2L, new ProductType(), 2L, 7L));

        testValuesSalesStatusId.add(null);
        testValuesSalesStatusId.add(3L);

        testValuesLogisticsStatusId.add(4L);
        testValuesLogisticsStatusId.add(null);
    }

    @org.junit.After
    public void tearDownChangeStatusOfProductData() {

        expectObjectsOfProducts.clear();
        testObjectsOfProducts.clear();
        testValuesLogisticsStatusId.clear();
        testValuesSalesStatusId.clear();
    }

    @Test
    public void testChangeStatusOfProduct() {
        int indexOfListTestParametersForMethod = 0;
        for (Map.Entry<String, Product> testObject : testObjectsOfProducts.entrySet()) {
            for (Map.Entry<String, Product> expectedObject : expectObjectsOfProducts.entrySet()) {

                Product testData = testObject.getValue();

                if (expectedObject.getKey().equals(testObject.getKey())) {
                    Product expected = expectedObject.getValue();

                    Product actual = productServiceImpl.changeStatusOfProduct(testData,
                            testValuesSalesStatusId.get(indexOfListTestParametersForMethod),
                                testValuesLogisticsStatusId.get(indexOfListTestParametersForMethod));

                    if (testValuesSalesStatusId.get(indexOfListTestParametersForMethod) != null) {
                        assertEquals(expected.getSalesStatus(), actual.getSalesStatus());
                    } else if (testValuesLogisticsStatusId.get(indexOfListTestParametersForMethod) != null) {
                        assertEquals(expected.getLogisticsStatus(), actual.getLogisticsStatus());
                    }
                }
            }
            indexOfListTestParametersForMethod++;
        }
    }
}