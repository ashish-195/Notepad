package Notepad;

import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.StyledEditorKit.FontSizeAction;

public class Notepad {
	JFrame frame;
	JTextArea textArea;
	JMenuBar menuBar;
	JMenu fileMenu, langMenu, formatMenu, commandPrompt;
	// File Menu Items
	JMenuItem itemNew, itemNewWindow, itemOpen, itemSaveAs, itemSave, itemExit;

	//

	JMenuItem itemWordWrap, itemFont, itemFontSize;

	JMenuItem itemCMD;
	BufferedReader br = null;
	BufferedWriter bw = null;

	String openPath = null;
	String openFileName = null;
	Boolean wrap = true;
	Font arial, timesRoman, consolas;
	String fontStyle = "Arial";
	int defsize = 30;

	public Notepad() {

		createFrame();
		createTextArea();
		createScrollBars();
		createMenuBar();
		createFileMenuItems();
		createFormatMenuItems();
		createCommandPromptItem();
		createLanguageItems();
	}

	public void createFrame() {
		frame = new JFrame("Notepad");
		frame.setSize(1920, 1080);
		Image icon = Toolkit.getDefaultToolkit().getImage("C:\\Users\\Ashish\\OneDrive\\Desktop\\579703.png");
		frame.setIconImage(icon);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	public void createTextArea() {
		textArea = new JTextArea();
		frame.add(textArea);
		textArea.setFont(new Font("Consolas", Font.PLAIN, defsize));

	}

	public void createScrollBars() {
		JScrollPane scroll = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setAlignmentX(JScrollPane.LEFT_ALIGNMENT);
		frame.add(scroll);

	}

	public void createMenuBar() {
		menuBar = new JMenuBar();

		frame.setJMenuBar(menuBar);
		fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		langMenu = new JMenu("Languages");
		menuBar.add(langMenu);
		formatMenu = new JMenu("Format");
		menuBar.add(formatMenu);
		commandPrompt = new JMenu("Command Prompt");
		menuBar.add(commandPrompt);

	}

	public void createFileMenuItems() {

		itemNew = new JMenuItem("New");
		fileMenu.add(itemNew);
		itemNew.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				textArea.setText("");
				frame.setTitle("Untitled-Notepad");

				openFileName = null;
				openPath = null;

			}
		});
		itemNewWindow = new JMenuItem("New Window");
		fileMenu.add(itemNewWindow);
		itemNewWindow.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				Notepad cr = new Notepad();
				cr.frame.setTitle("untitled");

			}
		});
		itemOpen = new JMenuItem("Open");
		fileMenu.add(itemOpen);
		itemOpen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				FileDialog fd = new FileDialog(frame, "Open", FileDialog.LOAD);
				fd.setVisible(true);
				String path = fd.getDirectory();
				String fileName = fd.getFile();
				if (fileName != null) {
					frame.setTitle(fileName);
					openPath = path;
					openFileName = fileName;
				}

				try {
					br = new BufferedReader(new FileReader(path + fileName));
					String sentence = br.readLine();
					textArea.setText("");
					while (sentence != null) {
						textArea.append(sentence + "\n");

						sentence = (br.readLine());
					}
				} catch (FileNotFoundException e2) {
					System.out.println("File not found");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					System.out.println("data could not be read");

				} catch (NullPointerException e1) {

				} finally {
					try {
						br.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						System.out.println("file could not be found");

					} catch (NullPointerException e1) {

					}
				}

			}
		});
		itemSave = new JMenuItem("Save");
		fileMenu.add(itemSave);
		itemSave.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (openFileName != null && openPath != null) {

					writeDataToFile(openFileName, openPath);

				} else {
					FileDialog fd = new FileDialog(frame, "Save As", FileDialog.SAVE);
					fd.setVisible(true);
					String path = fd.getDirectory();
					String fileName = fd.getFile();
					if (fileName != null && path != null) {

						writeDataToFile(openPath, openFileName);
						openFileName = fileName;
						openPath = path;
						frame.setTitle(fileName);

					}

				}

			}
		});
		itemSaveAs = new JMenuItem("Save As");
		fileMenu.add(itemSaveAs);
		itemSaveAs.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				FileDialog fd = new FileDialog(frame, "Save As", FileDialog.SAVE);
				fd.setVisible(true);
				String path = fd.getDirectory();
				String fileName = fd.getFile();
				if (fileName != null && path != null) {
					writeDataToFile(fileName, path);
				}

			}
		});
		itemExit = new JMenuItem("Exit");
		fileMenu.add(itemExit);
		itemExit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				frame.dispose();

			}
		});
	}

	public void createLanguageItems() {
		JMenuItem itemJava = new JMenuItem("Java");

		itemJava.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setLanguage("Java");
				openPath = null;
				openFileName = null;
			}
		});

		langMenu.add(itemJava);

		JMenuItem itemC = new JMenuItem("C");
		langMenu.add(itemC);

		itemC.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setLanguage("C");
				openPath = null;
				openFileName = null;
			}
		});

		JMenuItem itemHtml = new JMenuItem("HTML");
		langMenu.add(itemHtml);

		itemHtml.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setLanguage("HTML");
				openPath = null;
				openFileName = null;
			}
		});

		JMenuItem itemCpp = new JMenuItem("C++");
		langMenu.add(itemCpp);

		itemCpp.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setLanguage("Cpp");
				openPath = null;
				openFileName = null;
			}
		});

	}

	public void setLanguage(String lang) {
		BufferedReader br = null;
		try {

			br = new BufferedReader(new FileReader(
					"C:\\Users\\Ashish\\OneDrive\\Desktop\\Java Standalone\\formats\\" + lang + "Format.txt"));

			String sentence = br.readLine();
			textArea.setText(null);

			while (sentence != null) {
				textArea.append(sentence + "\n");
				sentence = br.readLine();
			}

		} catch (FileNotFoundException e1) {
			System.out.println("File not Found");
		} catch (IOException e1) {
			System.out.println("Data  could not be read ");
		} catch (NullPointerException e2) {

		} finally {
			try {
				br.close();
			} catch (IOException e1) {
				System.out.println("File could  not be close");
			} catch (NullPointerException e2) {

			}
		}
		openPath = "";
	}

	public void createFormatMenuItems() {
		// TODO Auto-generated method stub
		itemWordWrap = new JMenuItem("Word Wrap off");
		formatMenu.add(itemWordWrap);
		itemWordWrap.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (wrap == false) {
					textArea.setLineWrap(true);
					textArea.setWrapStyleWord(true);
					wrap = true;
					itemWordWrap.setText("Word-Wrap on");
				} else {
					textArea.setLineWrap(false);
					textArea.setWrapStyleWord(false);
					wrap = false;
					itemWordWrap.setText("Word-Wrap off");
				}
			}
		});

		itemFont = new JMenu("Font ");
		JMenuItem itemArial = new JMenuItem("Arial");
		itemArial.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				font("Arial");
			}
		});
		JMenuItem itemTimesNewRoman = new JMenuItem("Times New Roman");
		itemTimesNewRoman.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				font("Times New Roman");
			}
		});
		JMenuItem itemConsolas = new JMenuItem("Consolas");
		itemConsolas.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				font("Consolas");
			}
		});
		formatMenu.add(itemFont);
		itemFont.add(itemArial);
		itemFont.add(itemTimesNewRoman);
		itemFont.add(itemConsolas);

		itemFontSize = new JMenu("Font Size");

		formatMenu.add(itemFontSize);

		JMenuItem item10 = new JMenuItem("10");
		item10.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setFontSize(10);
			}
		});
		JMenuItem item14 = new JMenuItem("14");
		item14.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setFontSize(14);
			}
		});
		JMenuItem item18 = new JMenuItem("18");
		item18.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setFontSize(18);
			}
		});
		JMenuItem item22 = new JMenuItem("22");
		item22.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setFontSize(22);
			}
		});
		JMenuItem item26 = new JMenuItem("26");
		item26.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setFontSize(26);
			}
		});
		JMenuItem item30 = new JMenuItem("30");
		item30.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setFontSize(30);
			}
		});

		itemFontSize.add(item10);
		itemFontSize.add(item14);

		itemFontSize.add(item18);
		itemFontSize.add(item22);
		itemFontSize.add(item26);
		itemFontSize.add(item30);
		setFontSize(defsize);

	}

	public void setFontSize(int size) {
		arial = new Font("Arial", Font.PLAIN, size);
		timesRoman = new Font("Times New Roman", Font.PLAIN, size);
		consolas = new Font("Consolas", Font.PLAIN, size);
		font(fontStyle);

	}

	public void font(String font) {
		fontStyle = font;
		switch (font) {
		case "Arial":
			textArea.setFont(arial);
			break;
		case "Times New Roman":
			textArea.setFont(timesRoman);
			break;
		case "Consolas":
			textArea.setFont(consolas);
			break;

		default:
			break;
		}
	}

	public void createCommandPromptItem() {

		itemCMD = new JMenuItem("Open CMD");
		itemCMD.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				try {
					if (openPath != null) {
						Runtime.getRuntime().exec(new String[] { "cmd", "/C", "start" }, null, new File(openPath));
					}
				} catch (IOException e1) {

					System.out.println("Could not launch command prompt");

				} catch (NullPointerException e2) {
					System.out.println("Could not launch command prompt");
				}

			}
		});
		commandPrompt.add(itemCMD);

	}

	public void writeDataToFile(String fileName, String path) {
		openFileName = fileName;
		openPath = path;
		try {
			bw = new BufferedWriter(new FileWriter(path + fileName));
			String text = textArea.getText();
			bw.write(text);
		} catch (IOException e1) {
			System.out.println("Data Cannot be Written");
		} finally {
			try {
				bw.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (NullPointerException e1) {
				System.out.println("");
			}
		}

	}

}
