import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Date;
import javax.swing.*;

@SuppressWarnings("serial")
public class HastaGrafigi extends JPanel {
    private int width;
    private int height;
    private static final int BORDER_GAP = 30;
    private static final Color GRAPH_COLOR = Color.gray;
    private static final Color GRAPH_POINT_COLOR = new Color(0, 50, 50, 170);
    private static final Stroke GRAPH_STROKE = new BasicStroke(3f);
    private static final int GRAPH_POINT_WIDTH = 6;
    private ArrayList<Integer> scores;
    private ArrayList<java.sql.Date> scoreDates;
    private ArrayList<String> scoreTimes;
    private int maxNormalValue, minNormalValue;
    private int graphKind; //0:nabız, 1:sistolik_tansiyon, 2:diastolik_tansiyon, 3:ateş, 4:solunum, 5:kan şekeri, 6:oks. sat
    private int graphMax;
    private String graphHeader;

    public HastaGrafigi(ArrayList<Integer> scores, ArrayList<java.sql.Date> scoreDates, int i, int width, int height, ArrayList<String> scoreTimes) {
        this.scores = scores;
        this.scoreDates = scoreDates;
        this.graphKind = i;
        this.width = width;
        this.height = height;
        this.scoreTimes = scoreTimes;

        switch(graphKind){
            case 0: minNormalValue = 60; maxNormalValue = 100; graphMax = 160; graphHeader = "Nabız Grafiği (bpm)"; break;
            case 1: minNormalValue = 90; maxNormalValue = 120; graphMax = 200; graphHeader = "Sistolik Tansiyon Grafiği (mmHg)"; break;
            case 2: minNormalValue = 60; maxNormalValue = 80; graphMax = 140; graphHeader = "Diastolik Tansiyon Grafiği (mmHg)"; break;
            case 3: minNormalValue = 36; maxNormalValue = 38; graphMax = 50; graphHeader = "Vücut Sıcaklığı Grafiği (°C)"; break;
            case 4: minNormalValue = 12; maxNormalValue = 20; graphMax = 50; graphHeader = "Solunum Grafiği (br/min)"; break;
            case 5: minNormalValue = 70; maxNormalValue = 100; graphMax = 500; graphHeader = "Kan Şekeri Grafiği (mg/dL)"; break;
            case 6: minNormalValue = 90; maxNormalValue = 100; graphMax = 100; graphHeader = "Oksijen Satürasyonu Grafiği (%)"; break;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        double xScale = ((double) getWidth() - 2 * BORDER_GAP) / (scores.size() - 1);
        double yScale = ((double) getHeight() - 2 * BORDER_GAP) / (graphMax - 1);

        List<Point> graphPoints = new ArrayList<Point>();
        for (int i = 0; i < scores.size(); i++) {
            int x1 = (int) (i * xScale + BORDER_GAP);
            int y1 = (int) ((graphMax - scores.get(i)) * yScale + BORDER_GAP);
            graphPoints.add(new Point(x1, y1));
        }

        // x ve y eksenlerini oluşturma
        g2.drawLine(BORDER_GAP, getHeight() - BORDER_GAP, BORDER_GAP, BORDER_GAP);
        g2.drawLine(BORDER_GAP, getHeight() - BORDER_GAP, getWidth() - BORDER_GAP, getHeight() - BORDER_GAP);

        // x ekseni çizgilerini çizme
        for (int i = 0; i < scores.size() - 1; i++) {
            int x0 = (i + 1) * (getWidth() - BORDER_GAP * 2) / (scores.size() - 1) + BORDER_GAP;
            int x1 = x0;
            int y0 = getHeight() - BORDER_GAP;
            int y1 = y0 - GRAPH_POINT_WIDTH;
            g2.drawLine(x0, y0, x1, y1);
            //g2.drawString("str", x0 - 6, y0 + 10);
            LocalDate ld = ((java.sql.Date)scoreDates.get(i)).toLocalDate();
            g2.drawString( ld.getDayOfMonth() + " / " + ld.getMonthValue(), x0 - 13, y0 + 13);
            g2.drawString(scoreTimes.get(i).substring(0,2) + ":" + scoreTimes.get(i).substring(2,4), x0 - 13, y0 + 25);
        }

        Stroke oldStroke = g2.getStroke();
        g2.setColor(GRAPH_COLOR);
        g2.setStroke(GRAPH_STROKE);
        for (int i = 0; i < graphPoints.size() - 1; i++) {
            int x1 = graphPoints.get(i).x;
            int y1 = graphPoints.get(i).y;
            int x2 = graphPoints.get(i + 1).x;
            int y2 = graphPoints.get(i + 1).y;
            g2.drawLine(x1, y1, x2, y2);
        }

        g2.setStroke(oldStroke);
        g2.setColor(GRAPH_POINT_COLOR);
        for (int i = 0; i < graphPoints.size(); i++) {
            int x = graphPoints.get(i).x - GRAPH_POINT_WIDTH / 2;
            int y = graphPoints.get(i).y - GRAPH_POINT_WIDTH / 2;;
            int ovalW = GRAPH_POINT_WIDTH;
            int ovalH = GRAPH_POINT_WIDTH;
            g2.fillOval(x, y, ovalW, ovalH);

            g2.drawString( scores.get(i) + "", x, y - 3);
        }



        g2.setColor(Color.RED);
        g2.drawLine(BORDER_GAP, (int)((graphMax - maxNormalValue) * yScale + BORDER_GAP), getWidth()-BORDER_GAP, (int)((graphMax - maxNormalValue) * yScale + BORDER_GAP));
        g2.drawString(maxNormalValue + "", BORDER_GAP - 21, (int)((graphMax - maxNormalValue) * yScale + BORDER_GAP) + 5);

        g2.setColor(Color.BLUE);
        g2.drawLine(BORDER_GAP, (int)((graphMax - minNormalValue) * yScale + BORDER_GAP), getWidth()-BORDER_GAP, (int)((graphMax - minNormalValue) * yScale + BORDER_GAP));
        g2.drawString(minNormalValue + "", BORDER_GAP - 21, (int)((graphMax - minNormalValue) * yScale + BORDER_GAP) + 5);

        g2.setColor(Color.black);

        g2.drawString(graphHeader, BORDER_GAP, BORDER_GAP - 10);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }

    public int getGraphKind() {
        return graphKind;
    }
