package org.matt.controllers;

import javafx.scene.control.TableView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import org.controlsfx.glyphfont.FontAwesome;
import org.controlsfx.glyphfont.Glyph;

public class GlobalProperties {
    private static int currentUserId;
    private static int currentMovieId;
    public final static int ROWS_PER_PAGE = 50;

    public static int getCurrentMovieId() {
        return currentMovieId;
    }

    public static void setCurrentMovieId(int currentMovieId) {
        GlobalProperties.currentMovieId = currentMovieId;
    }

    public static int getCurrentUserId() {
        return currentUserId;
    }

    public static void setCurrentUserId(int currentUserId) {
        GlobalProperties.currentUserId = currentUserId;
    }

    public static Glyph gfxGlyph(FontAwesome.Glyph fontGlyph, Color color, int size) {
        return Glyph.create("FontAwesome|" + fontGlyph.name()).sizeFactor(size).color(color).useGradientEffect();
    }

    public static void autoResizeColumns(TableView<?> table) {
        table.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        table.getColumns().forEach((column) -> {
            Text t = new Text(column.getText());
            double max = t.getLayoutBounds().getWidth();
            for (int i = 0; i < table.getItems().size(); i++) {
                if (column.getCellData(i) != null) {
                    t = new Text(column.getCellData(i).toString());
                    double calcwidth = t.getLayoutBounds().getWidth();
                    if (calcwidth > max) {
                        max = calcwidth;
                    }
                }
            }
            column.setPrefWidth(max + 10.0d);
        });
    }
}
