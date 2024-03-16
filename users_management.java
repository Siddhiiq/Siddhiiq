package Crud_Swing;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.sql.DriverManager;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.border.LineBorder;
import javax.swing.table.TableModel;

import java.awt.Color;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.sql.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class users_management {

	private JFrame frmCrudmysql;
	private JTextField textId;
	private JTextField textName;
	private JTextField textAge;
	private JTextField textCity;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					users_management window = new users_management();
					window.frmCrudmysql.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public users_management() {
		initialize();
		Connect();
		loadData();
	}
	
	
	
// Database Connection
	Connection con=null;
	PreparedStatement pst;
	ResultSet rs;
	
	public void Connect() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbsiddhu","root","Allah786@@");
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	// clear all
	public void clear() {
		textId.setText("");
		textName.setText("");
		textAge.setText("");
		textCity.setText("");
		textName.requestFocus();
		
	}
	
	//Load Data 
	
	public void loadData()
	{
		try {
			pst=con.prepareStatement("select* from users");
			rs= pst.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs));
			}catch (Exception ex) {
				ex.printStackTrace();
			}
		
	}
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmCrudmysql = new JFrame();
		frmCrudmysql.setFont(new Font("Dialog", Font.BOLD, 16));
		frmCrudmysql.setTitle("Crud_mysql");
		frmCrudmysql.setBounds(100, 100, 1110, 825);
		frmCrudmysql.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmCrudmysql.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("User Management System");
		lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 16));
		lblNewLabel.setBounds(12, 35, 251, 15);
		frmCrudmysql.getContentPane().add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel.setBounds(12, 77, 492, 370);
		frmCrudmysql.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblId = new JLabel("Id");
		lblId.setFont(new Font("Dialog", Font.BOLD, 16));
		lblId.setBounds(40, 47, 70, 15);
		panel.add(lblId);
		
		JLabel lblName = new JLabel("Name");
		lblName.setFont(new Font("Dialog", Font.BOLD, 16));
		lblName.setBounds(40, 109, 70, 15);
		panel.add(lblName);
		
		JLabel lblAge = new JLabel("Age");
		lblAge.setFont(new Font("Dialog", Font.BOLD, 16));
		lblAge.setBounds(40, 167, 70, 15);
		panel.add(lblAge);
		
		JLabel lblCity = new JLabel("City");
		lblCity.setFont(new Font("Dialog", Font.BOLD, 16));
		lblCity.setBounds(40, 232, 70, 15);
		panel.add(lblCity);
		
		textId = new JTextField();
		textId.setEditable(false);
		textId.setFont(new Font("Dialog", Font.PLAIN, 14));
		textId.setBounds(140, 46, 315, 30);
		panel.add(textId);
		textId.setColumns(10);
		
		textName = new JTextField();
		textName.setFont(new Font("Dialog", Font.PLAIN, 14));
		textName.setBounds(140, 103, 315, 29);
		panel.add(textName);
		textName.setColumns(10);
		
		textAge = new JTextField();
		textAge.setFont(new Font("Dialog", Font.PLAIN, 14));
		textAge.setBounds(140, 156, 315, 29);
		panel.add(textAge);
		textAge.setColumns(10);
		
		textCity = new JTextField();
		textCity.setFont(new Font("Dialog", Font.PLAIN, 14));
		textCity.setBounds(140, 220, 315, 30);
		panel.add(textCity);
		textCity.setColumns(10);
		
		JButton BtnSave = new JButton("Save");
		BtnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id=textId.getText();
				String name=textName.getText();
				String age=textAge.getText();
				String city=textCity.getText();
				
				if(name==null|| name.isEmpty() || name.trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please Enter Name");
					textName.requestFocus();
					return;
				}
				if(age==null|| age.isEmpty() || age.trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please Enter Age");
					textAge.requestFocus();
					return;
				}
				if(city==null|| city.isEmpty() || city.trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please Enter City");
					textCity.requestFocus();
					return;
				}
				if(textId.getText().isEmpty()) {
					try {
						String sql = "insert into users (NAME,AGE,CITY) values (?,?,?)";
						pst = con.prepareStatement(sql);
						pst.setString(1, name);
						pst.setString(2, age);
						pst.setString(3, city);
						pst.executeUpdate();
						JOptionPane.showMessageDialog(null,"Data insert success");
						clear();
						loadData();
						
					}catch(Exception e1) {
						e1.printStackTrace();					}
					
				}
				
						}
		});
		BtnSave.setBounds(140, 283, 95, 25);
		panel.add(BtnSave);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id=textId.getText();
				String name=textName.getText();
				String age=textAge.getText();
				String city=textCity.getText();
				
				if(name==null|| name.isEmpty() || name.trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please Enter Name");
					textName.requestFocus();
					return;
				}
				if(age==null|| age.isEmpty() || age.trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please Enter Age");
					textAge.requestFocus();
					return;
				}
				if(city==null|| city.isEmpty() || city.trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please Enter City");
					textCity.requestFocus();
					return;
				}
				if(!textId.getText().isEmpty()) {
					try {
						String sql = "update users set NAME=?,AGE=?,CITY=? where ID=?";
						pst = con.prepareStatement(sql);
						pst.setString(1, name);
						pst.setString(2, age);
						pst.setString(3, city);
						pst.setString(4, id);
						pst.executeUpdate();
						JOptionPane.showMessageDialog(null,"Data update success");
						clear();
						loadData();
						
					}catch(Exception e1) {
						e1.printStackTrace();					}
					
				}
				
				}
			
		});
		
		btnUpdate.setBounds(256, 283, 89, 25);
		panel.add(btnUpdate);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String id=textId.getText();
				if(!textId.getText().isEmpty()) {
					int result=JOptionPane.showConfirmDialog(null, "Sure? You want to Delete?","Delete", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
					
					if(result==JOptionPane.YES_OPTION) {
						try {
							String sql="delete from users where ID=?";
							pst=con.prepareStatement(sql);
							pst.setString(1, id);
							pst.executeUpdate();
							JOptionPane.showMessageDialog(null,"Data deleted Success");
							clear();
							loadData();
							
						}catch(SQLException e1) {
							e1.printStackTrace();						}
						
					}
				}
				}
		});
		btnDelete.setBounds(371, 283, 84, 25);
		panel.add(btnDelete);
		
	    JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(563, 77, 506, 403);
		frmCrudmysql.getContentPane().add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int index =table.getSelectedRow();
				TableModel model = table.getModel();
				
				textId.setText(model.getValueAt(index,0).toString());
				textName.setText(model.getValueAt(index,1).toString());
				textAge.setText(model.getValueAt(index,2).toString());
				textCity.setText(model.getValueAt(index,3).toString());
			}
		});
		scrollPane.setViewportView(table);
	}
}
