package strategy;

import lombok.AllArgsConstructor;

@AllArgsConstructor
class ImageStorage {

    private Compressor compressor;
    private Filter filter;

    void store(String fileName) {
        compressor.compress(fileName);
        filter.applyFor(fileName);
    }

    enum Compressor {
        JPEG {
            @Override
            void compress(String fileName) {
                System.out.println("JPEG compressing running for: " + fileName);
            }
        },
        PNG {
            @Override
            void compress(String fileName) {
                System.out.println("PNG compressing running for: " + fileName);
            }
        };

        abstract void compress(String fileName);
    }

    enum Filter {
        BLACK_AND_WHITE {
            @Override
            void applyFor(String fileName) {
                System.out.println("Applying BLACK_AND_WHITE filter for: " + fileName);
            }
        },
        HIGH_CONTRAST {
            @Override
            void applyFor(String fileName) {
                System.out.println("Applying HIGH_CONTRAST filter for: " + fileName);
            }
        };

        abstract void applyFor(String fileName);
    }

}
