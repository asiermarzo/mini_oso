package oso.clients;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import oso.common.Tablero;
import oso.server.Jugador;


public class OsoGUI extends javax.swing.JFrame implements ActionListener{
    private JButton[][] botones;
    Jugador jugador;
    boolean activado = false;
    
    public OsoGUI(Jugador jugador) {
        this.jugador = jugador;
        initComponents();
        pack();
    }
    
    void iniciarBotones(int xDim, int yDim){
        panelJuego.removeAll();
        botones = new JButton[xDim][yDim];
        panelJuego.setLayout( new GridLayout(xDim, yDim));
        for (int x = 0; x < xDim; ++x){
            for(int y = 0; y < yDim; ++y){
                final JButton b = new JButton();
                b.setActionCommand(x + "," + y);
                b.addActionListener(this);
                botones[x][y] = b;
                panelJuego.add(b);
            }
        }
        pack();
    }
    

      
     @Override
    public void actionPerformed(ActionEvent e) {
        if (activado == false)
            return;
        
        String command = e.getActionCommand();
        String[] s = command.split(",");
        if (s.length != 2)
            return;
        
        char letra = oRadio.isSelected() ? 'o' : 's';
        int x = Integer.parseInt( s[0] );
        int y = Integer.parseInt( s[1] );
        JButton button = (JButton) e.getSource();
        
        jugador.mandarJugada(x,y,letra);
        activar(false);
    }
    
    void actualizarPuntos(int tuyos, int otro){
        yourOsosText.setText( tuyos + "");
        oppoOsosText.setText( otro + "");
    }
    
    void actualizar(Tablero tablero){
        final int w = tablero.getxDim();
        final int h = tablero.getyDim();
        for (int x = 0; x < w; ++x) {
            for (int y = 0; y < h; ++y) {
                JButton button = botones[x][y];
                char letter = tablero.letraEn(x, y);
                boolean used = tablero.usadaEn(x, y);
                
                if (letter == Tablero.CHAR_VACIO){
                    button.setText("");
                    button.setEnabled(true);
                }else{
                    button.setText(letter + "");
                    button.setEnabled(false);
                    button.setForeground( used ? Color.RED : Color.GREEN);
                }
            }
        }
    }
    
    void activar(boolean b) {
        activado = b;
        oRadio.setEnabled( b );
        sRadio.setEnabled( b );
    }
    
    
    void terminar() {
        activar(false);
        mainLabel.setText("FIN");
        for (JButton[] bs : botones) {
            for (JButton b : bs) {
                b.setEnabled(false);
            }
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        oOrSGroup = new javax.swing.ButtonGroup();
        mainLabel = new javax.swing.JLabel();
        yourOsosText = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        oppoOsosText = new javax.swing.JTextField();
        oRadio = new javax.swing.JRadioButton();
        sRadio = new javax.swing.JRadioButton();
        panelJuego = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(288, 294));

        mainLabel.setText("OSOS");

        yourOsosText.setEditable(false);
        yourOsosText.setText("0");

        jLabel2.setText("Otro:");

        oppoOsosText.setEditable(false);
        oppoOsosText.setText("0");

        oOrSGroup.add(oRadio);
        oRadio.setSelected(true);
        oRadio.setText("o");

        oOrSGroup.add(sRadio);
        sRadio.setText("s");

        panelJuego.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout panelJuegoLayout = new javax.swing.GroupLayout(panelJuego);
        panelJuego.setLayout(panelJuegoLayout);
        panelJuegoLayout.setHorizontalGroup(
            panelJuegoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelJuegoLayout.setVerticalGroup(
            panelJuegoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 208, Short.MAX_VALUE)
        );

        jLabel3.setText("Tu:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelJuego, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(oRadio)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sRadio)
                        .addGap(0, 188, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(mainLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(yourOsosText, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(oppoOsosText, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mainLabel)
                    .addComponent(yourOsosText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2)
                    .addComponent(oppoOsosText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(oRadio)
                    .addComponent(sRadio))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelJuego, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


  

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel mainLabel;
    private javax.swing.ButtonGroup oOrSGroup;
    private javax.swing.JRadioButton oRadio;
    private javax.swing.JTextField oppoOsosText;
    private javax.swing.JPanel panelJuego;
    private javax.swing.JRadioButton sRadio;
    private javax.swing.JTextField yourOsosText;
    // End of variables declaration//GEN-END:variables


    

   
}
