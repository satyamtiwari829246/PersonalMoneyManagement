package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.sql.ResultSet;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import dao.SecurityLogDAO;
import dao.Tranctiondao;

public class Dashboard extends JFrame {

    public Dashboard(int userId) {
        Tranctiondao transactionDAO = new Tranctiondao();

        double totalIncome =
                transactionDAO.getTotalIncome(userId);

        double totalExpense =
                transactionDAO.getTotalExpense(userId);

        double totalBalance =
                totalIncome - totalExpense;

        setTitle("Personal Money Management");
        setSize(1000, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        //Main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(245, 247, 250));


        //slidbar

        JPanel sidebar = new JPanel();
        sidebar.setPreferredSize(new Dimension(220, 650));
        sidebar.setBackground(new Color(30, 35, 45));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));

        JLabel logo = new JLabel("  Kuber Manager");
        logo.setForeground(Color.WHITE);
        logo.setFont(new Font("Arial", Font.BOLD, 22));
        logo.setBorder(
                BorderFactory.createEmptyBorder(
                        30, 10, 40, 10
                )
        );

        sidebar.add(logo);

        JButton dashboardBtn =
                new JButton("Dashboard");

        JButton incomeBtn =
                new JButton("Income");


        incomeBtn.addActionListener(e -> {
            new IncomeHistory(userId);
        });

        JButton expenseBtn =
                new JButton("Expenses");
        expenseBtn.addActionListener(e -> {
            new ExpenseHistory(userId);
        });
        JButton addTransactionBtn =
                new JButton("Add Transaction");


        JButton transactionBtn =
                new JButton("Transactions");
        transactionBtn.addActionListener(e -> {
            new TransactionHistory(userId);
        });
        JButton goalsBtn = new JButton("Financial Goal");
        goalsBtn.addActionListener(e -> {
            new GoalFrame(userId);
        });
        JButton budgetBtn =
                new JButton("Budget");
        budgetBtn.addActionListener(e -> {
            new BudgetFrame(userId);
        });
        JButton viewBudgetBtn = new JButton("View Budgets");

        viewBudgetBtn.addActionListener(e -> {
            new BudgetListFrame(userId);
        });
        JButton settingsBtn =
                new JButton("Settings");
        settingsBtn.addActionListener(e -> {
            new SettingsFrame(userId);
        });

        JButton logoutBtn =
                new JButton("Logout");

        logoutBtn.addActionListener(e -> {
            SecurityLogDAO logDAO = new SecurityLogDAO();

            logDAO.addLog(
                    userId,
                    "LOGOUT",
                    "User logged out"
            );

            dispose();
            new Login();
        });


        sidebar.add(dashboardBtn);
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(addTransactionBtn);

        sidebar.add(
                Box.createVerticalStrut(10)
        );
        addTransactionBtn.addActionListener(e -> {

            new Addtransaction(userId);

        });

        sidebar.add(incomeBtn);
        sidebar.add(Box.createVerticalStrut(10));

        sidebar.add(expenseBtn);
        sidebar.add(Box.createVerticalStrut(10));

        sidebar.add(transactionBtn);
        sidebar.add(Box.createVerticalStrut(10));

        sidebar.add(budgetBtn);
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(viewBudgetBtn);
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(settingsBtn);

        sidebar.add(Box.createVerticalGlue());

        sidebar.add(logoutBtn);


        // ================= CONTENT =================

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBorder(
                BorderFactory.createEmptyBorder(
                        30, 35, 30, 35
                )
        );
        content.setBackground(
                new Color(245, 247, 250)
        );


        // Welcome
        JLabel welcome =
                new JLabel("Welcome back! 👋");

        welcome.setFont(
                new Font("Arial", Font.BOLD, 28)
        );

        content.add(welcome);

        content.add(
                Box.createVerticalStrut(10)
        );


        JLabel subtitle =
                new JLabel(
                        "Here's your financial overview"
                );

        subtitle.setFont(
                new Font("Arial", Font.PLAIN, 16)
        );

        content.add(subtitle);

        content.add(
                Box.createVerticalStrut(30)
        );
        //lower income

        if (totalBalance < 0) {
            JLabel warningLabel = new JLabel(
                    "⚠ Warning: Your balance is negative! " +
                            "Your expenses are greater than your income."
            );

            warningLabel.setFont(
                    new Font("Arial", Font.BOLD, 14)
            );
            warningLabel.setForeground(new Color(220, 38, 38));

            content.add(warningLabel);
            content.add(Box.createVerticalStrut(15));
        }


        // ================= CARDS =================

        JPanel cards = new JPanel(
                new GridLayout(1, 3, 20, 0)
        );

        cards.setOpaque(false);


        JPanel balanceCard =
                createCard(
                        "Total Balance",
                        "₹ " + totalBalance
                );

        JPanel incomeCard =
                createCard(
                        "Total Income",
                        "₹ " + totalIncome
                );

        JPanel expenseCard =
                createCard(
                        "Total Expenses",
                        "₹ " + totalExpense
                );


        cards.add(balanceCard);
        cards.add(incomeCard);
        cards.add(expenseCard);


        content.add(cards);

        content.add(
                Box.createVerticalStrut(30)
        );


        // Recent transactions

        JLabel recent =
                new JLabel("Recent Transactions");

        recent.setFont(
                new Font("Arial", Font.BOLD, 20)
        );

        content.add(recent);

        content.add(
                Box.createVerticalStrut(15)
        );


        JPanel transactionPanel =
                new JPanel();

        transactionPanel.setBackground(
                Color.WHITE
        );

        transactionPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        20, 20, 20, 20
                )
        );

        transactionPanel.setLayout(
                new GridLayout(5, 3, 10, 10)
        );
        transactionPanel.add(new JLabel("Category"));
        transactionPanel.add(new JLabel("Type"));
        transactionPanel.add(new JLabel("Amount"));
        try {

            ResultSet rs =
                    transactionDAO.getRecentTransactions(userId);

            while (rs != null && rs.next()) {

                String category =
                        rs.getString("category");

                String type =
                        rs.getString("type");

                double amount =
                        rs.getDouble("amount");

                transactionPanel.add(
                        new JLabel(category)
                );

                transactionPanel.add(
                        new JLabel(type)
                );

                String amountText;

                if (type.equals("Income")) {
                    amountText = "+ ₹" + amount;
                } else {
                    amountText = "- ₹" + amount;
                }

                transactionPanel.add(
                        new JLabel(amountText)
                );
            }

        } catch (Exception e) {

            e.printStackTrace();
        }




        content.add(transactionPanel);


        // Add panels

        mainPanel.add(
                sidebar,
                BorderLayout.WEST
        );

        mainPanel.add(
                content,
                BorderLayout.CENTER
        );

        add(mainPanel);

        setVisible(true);
    }


    // ================= CARD METHOD =================

    private JPanel createCard(
            String title,
            String amount
    ) {

        JPanel card = new JPanel();

        card.setLayout(
                new BoxLayout(
                        card,
                        BoxLayout.Y_AXIS
                )
        );

        card.setBackground(Color.WHITE);

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(220, 220, 220)
                        ),
                        BorderFactory.createEmptyBorder(
                                20, 20, 20, 20
                        )
                )
        );


        JLabel titleLabel =
                new JLabel(title);

        titleLabel.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        15
                )
        );


        JLabel amountLabel =
                new JLabel(amount);

        amountLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        24
                )
        );


        card.add(titleLabel);

        card.add(
                Box.createVerticalStrut(10)
        );

        card.add(amountLabel);


        return card;
    }


    // ================= MAIN =================

    public static void main(String[] args) {

        new Dashboard(1);

    }
}