import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent;
import java.awt.Color;

  

public class CalculadoraDerivada extends JFrame {

    private static final long serialVersionUID = 1L;
// Cria os campos de texto
    private JTextField textFieldFuncao;
    private JTextField textFieldValor;
    private JTextField textFieldResultado;

    public CalculadoraDerivada() {
        initUI();
    }
// Cria o Grid e insere os campos de texto, botões e labels dentro
    private void initUI() {
        JPanel panel = new JPanel(new GridLayout(4, 2, 5, 5));

        panel.add(new JLabel("Função:")).setForeground(Color.WHITE);
        textFieldFuncao = new JTextField("x", 20);
        panel.add(textFieldFuncao);
        // Adicionar Listener para o campo de texto da função para exibir o texto de dica, chamando o método
            textFieldFuncao.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                exibirToolTip();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                exibirToolTip();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                exibirToolTip();
            }
        });
/**
 setForegrounds para alterar a cor dos textos de título para branco.
 setBackground para alterar a cor do plano de fundo do botão Calcular para preto.
 setBackground para alterar a cor do plano de fundo do programa para azul.
 */
        panel.add(new JLabel("Valor:")).setForeground(Color.WHITE);
        textFieldValor = new JTextField("0", 20);
        panel.add(textFieldValor);

        panel.add(new JLabel());
        JButton buttonCalcular = new JButton("Calcular");
        buttonCalcular.setBackground(Color.BLACK);
        buttonCalcular.setForeground(Color.WHITE);
        panel.add(buttonCalcular);

        panel.add(new JLabel("Resultado:")).setForeground(Color.WHITE);
        textFieldResultado = new JTextField(20);
        textFieldResultado.setEditable(false);
        panel.add(textFieldResultado);

        add(panel);
        panel.setBackground(Color.BLUE);
        setTitle("Calculadora de Derivadas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 150);
        setLocationRelativeTo(null);
// Ação para o Botão Calcular, com o Evento de Cálculo da Derivada
        buttonCalcular.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String funcao = textFieldFuncao.getText();
                double valor = Double.parseDouble(textFieldValor.getText());

                // calcular a derivada da função no valor especificado
                double resultado = calcularDerivada(funcao, valor);
                
                // substituir x pelo valor numérico correspondente
String novaExpressao = funcao.replaceAll("x", Double.toString(valor));

                // exibir o resultado no campo de texto de saída
                textFieldResultado.setText(Double.toString(resultado));
            }
        });
    }

// Exibe a legenda/dica avisando sobre o uso do prefixo Math    
private void exibirToolTip() {
    if (textFieldFuncao.getText() != null) { // Se houver algo sendo digitado no textfield
        textFieldFuncao.setToolTipText("Para usar funções trigonométricas e outras funções matemáticas, use o prefixo Math., por exemplo: Math.sin(x), Math.sqrt(x)");
    } else {
        textFieldFuncao.setToolTipText(null);
    }
}

// Método de Cálculo da Derivada
    public double calcularDerivada(String funcao, double valor) {
    ScriptEngineManager mgr = new ScriptEngineManager();
    ScriptEngine engine = mgr.getEngineByName("JavaScript");
    double h = 0.0001;

    try {
        // calcular o valor da função para x + h
        double fxh = Double.parseDouble(engine.eval(funcao.replaceAll("x", Double.toString(valor + h))).toString());

        // calcular o valor da função para x - h
        double fxh_ = Double.parseDouble(engine.eval(funcao.replaceAll("x", Double.toString(valor - h))).toString());

        // calcular a derivada usando a fórmula de diferenças finitas
        double resultado = (fxh - fxh_) / (2 * h);

        return resultado;
    } catch (ScriptException e) {
        e.printStackTrace();
        return Double.NaN;
    }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            CalculadoraDerivada calculadora = new CalculadoraDerivada();
            calculadora.setVisible(true);
        });
    }
}
