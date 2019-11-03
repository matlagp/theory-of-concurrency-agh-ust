package tw;

public class TablePrinter {
    private String[][] table;
    private Character verticalSeparator;
    private Character horizontalSeparator;
    private Character cornerSeparator;
    private Character paddingCharacter;
    private int cellWidth;
    private int cellsInRow;
    private int cellPaddingLength = 2;                // Two spaces on either side of a cell

    public TablePrinter(String[][] table,
                        Character verticalSeparator,
                        Character horizontalSeparator,
                        Character cornerSeparator,
                        Character paddingCharacter) {
        this.table = table;
        this.verticalSeparator = verticalSeparator;
        this.horizontalSeparator = horizontalSeparator;
        this.cornerSeparator = cornerSeparator;
        this.paddingCharacter = paddingCharacter;
        this.cellWidth = findWidestCellWidth();
        this.cellsInRow = table[0].length;
    }

    public void print() {
        StringBuilder sb = new StringBuilder();
        for (String[] row: table) {
            sb.append(makeHorizontalLine()).append("\n");
            for (String cell: row) {
                sb.append(verticalSeparator)
                  .append(addPadding(cell));
            }
            sb.append(verticalSeparator).append("\n");
        }
        sb.append(makeHorizontalLine());
        System.out.println(sb.toString());
    }

    private String makeHorizontalLine() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < cellsInRow; i++) {
            sb.append(cornerSeparator)
              .append(repeat(horizontalSeparator, cellWidth));
        }

        return sb.append(cornerSeparator).toString();
    }

    private String addPadding(String cell) {
        StringBuilder sb = new StringBuilder();
        int backPadLength = cellPaddingLength/2;
        int frontPadLength = cellWidth - cell.length() - backPadLength;

        return sb.append(repeat(paddingCharacter, frontPadLength))
                .append(cell)
                .append(repeat(paddingCharacter, backPadLength))
                .toString();
    }

    private String repeat(Character character, int numberOfReps) {
        return new String(new char[numberOfReps]).replace('\0', character);
    }

    private int findWidestCellWidth() {
        int width = 0;
        for (String[] row: table) {
            for (String cell: row) {
                if (cell.length() > width) {
                    width = cell.length();
                }
            }
        }

        return width + cellPaddingLength;
    }
}
