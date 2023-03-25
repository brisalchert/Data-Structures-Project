package Products;

import java.util.Comparator;

public enum SortCategory implements Comparator<Product> {
    PriceExpensiveToCheap {
        @Override
        public int compare(Product o1, Product o2) {
            Comparator<Double> comp = Comparator.reverseOrder();

            return comp.compare(o1.getPrice(), o2.getPrice());
        }
    },
    PriceCheapToExpensive {
        @Override
        public int compare(Product o1, Product o2) {
            return Double.compare(o1.getPrice(), o2.getPrice());
        }
    },
    DateOldToNew {
        @Override
        public int compare(Product o1, Product o2) {
            if (o1.getDate() == o2.getDate()) {
                return 0;
            }

            if (o1.getDate().before(o2.getDate())) {
                return -1;
            }

            return 1;
        }
    },
    DateNewToOld {
        @Override
        public int compare(Product o1, Product o2) {
            if (o1.getDate() == o2.getDate()) {
                return 0;
            }

            if (o1.getDate().after(o2.getDate())) {
                return -1;
            }

            return 1;
        }
    };

    private final double[] MAX_VALUES = {1000, 1000};

    /**
     * Gets the maximum allowed value for a particular sorting category
     * @param sortCategory the sorting category
     * @return the maximum allowed value
     */
    public double getMax(SortCategory sortCategory) {
        return MAX_VALUES[sortCategory.ordinal() / 2];
    }

    /**
     * Gets the value associated with the sorting category
     * @param sortCategory the sorting category
     * @param product the product to get the value from
     * @return the value related to the sorting category
     */
    public double getValue(SortCategory sortCategory, Product product) {
        switch (sortCategory) {
            case PriceExpensiveToCheap -> {
                return sortCategory.getMax(sortCategory) - product.getPrice();
            }
            case PriceCheapToExpensive -> {
                return product.getPrice();
            }
            case DateNewToOld -> {
                return sortCategory.getMax(sortCategory) - product.getDaysAfterMinDay();
            }
            case DateOldToNew -> {
                return product.getDaysAfterMinDay();
            }
            default -> {
                return 0;
            }
        }
    }
}
