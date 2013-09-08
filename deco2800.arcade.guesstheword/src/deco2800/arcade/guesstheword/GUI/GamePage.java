package deco2800.arcade.guesstheword.GUI;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import deco2800.arcade.guesstheword.gameplay.WordShuffler;

/**
 *
 * @author duangui
 */
public class GamePage extends javax.swing.JPanel {
	
	private javax.swing.JLabel pictureLabel;
    // Variables declaration - do not modify                     
    private javax.swing.JButton clearButton;
    private javax.swing.JPanel textfieldPanel;
    private javax.swing.JButton nextButton;
    private javax.swing.JPanel picturePanel;
    private javax.swing.JButton submitButton;
    private javax.swing.JButton textButton1;
    private javax.swing.JButton textButton10;
    private javax.swing.JButton textButton2;
    private javax.swing.JButton textButton3;
    private javax.swing.JButton textButton4;
    private javax.swing.JButton textButton5;
    private javax.swing.JButton textButton6;
    private javax.swing.JButton textButton7;
    private javax.swing.JButton textButton8;
    private javax.swing.JButton textButton9;
    private javax.swing.JPanel textButtonPanel;
    private javax.swing.JTextField textfield1;
    private javax.swing.JTextField textfield2;
    private javax.swing.JTextField textfield3;
    private javax.swing.JTextField textfield4;
    private javax.swing.JLabel titleLabel;
    // End of variables declaration    
    
    private ArrayList<javax.swing.JButton> buttonList;
    private int nextButtonCount = 0, scoreCounter = 0 , score =0;;
    private String rain = "RAIN";
    public ImageIcon image;
    /**
     * Creates new form GamePage
     */
    public GamePage() {
        initComponents();
    }
                       
    private void initComponents() {
    	
        titleLabel = new javax.swing.JLabel("Guess The Word");
        
        //Picture panel
        picturePanel = new javax.swing.JPanel();
        
        nextButton = new javax.swing.JButton("Next Picture");
        
        
        //textfields for answering
        textfieldPanel = new javax.swing.JPanel();
        textfield2 = new javax.swing.JTextField();
        textfield3 = new javax.swing.JTextField();
        textfield4 = new javax.swing.JTextField();
        textfield1 = new javax.swing.JTextField();
        
        
        submitButton = new javax.swing.JButton();
        clearButton = new javax.swing.JButton();
        
        buttonList = new ArrayList<javax.swing.JButton>();

        // Text Buttons
        textButtonPanel = new javax.swing.JPanel();
        textButton1 = new javax.swing.JButton(); 
        buttonList.add(textButton1);
        textButton2 = new javax.swing.JButton();
        buttonList.add(textButton2);
        textButton3 = new javax.swing.JButton();
        buttonList.add(textButton3);
        textButton4 = new javax.swing.JButton();
        buttonList.add(textButton4);
        textButton5 = new javax.swing.JButton();
        buttonList.add(textButton5);
        textButton6 = new javax.swing.JButton();
        buttonList.add(textButton6);
        textButton7 = new javax.swing.JButton();
        buttonList.add(textButton7);
        textButton8 = new javax.swing.JButton();
        buttonList.add(textButton8);
        textButton9 = new javax.swing.JButton();
        buttonList.add(textButton9);
        textButton10 = new javax.swing.JButton();
        buttonList.add(textButton10);

        titleLabel.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N

        image = new ImageIcon("images/rain1.jpg");
        
        pictureLabel = new javax.swing.JLabel();
        pictureLabel.setPreferredSize(new java.awt.Dimension(400, 200));
        nextButton.addActionListener(new NextButtonListener());
        
        // Setting the layout for the picture panel 
        javax.swing.GroupLayout picturePanelLayout = new javax.swing.GroupLayout(picturePanel);
        picturePanel.setLayout(picturePanelLayout);
        picturePanelLayout.setHorizontalGroup(
            picturePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, picturePanelLayout.createSequentialGroup()
                .addGap(0, 383, Short.MAX_VALUE)
                .addComponent(nextButton))
            .addGroup(picturePanelLayout.createSequentialGroup()
                .addComponent(pictureLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        picturePanelLayout.setVerticalGroup(
            picturePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, picturePanelLayout.createSequentialGroup()
                .addComponent(pictureLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(nextButton))
        );
        // TEXT FIELDS
        textfield1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        textfield1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        
        textfield2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        textfield2.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        textfield3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        textfield3.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        textfield4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        textfield4.setHorizontalAlignment(javax.swing.JTextField.CENTER);


        javax.swing.GroupLayout textfieldPanelLayout = new javax.swing.GroupLayout(textfieldPanel);
        textfieldPanel.setLayout(textfieldPanelLayout);
        textfieldPanelLayout.setHorizontalGroup(
            textfieldPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(textfieldPanelLayout.createSequentialGroup()
                .addContainerGap(88, Short.MAX_VALUE)
                .addComponent(textfield1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(textfield2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(textfield3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(textfield4, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(89, 89, 89))
        );
        textfieldPanelLayout.setVerticalGroup(
            textfieldPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(textfieldPanelLayout.createSequentialGroup()
                .addGroup(textfieldPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(textfield1, javax.swing.GroupLayout.DEFAULT_SIZE, 49, Short.MAX_VALUE)
                    .addComponent(textfield2)
                    .addComponent(textfield3)
                    .addComponent(textfield4))
                .addContainerGap())
        );

        submitButton.setText("SUBMIT");
        submitButton.addActionListener(new TextButtonListener());
        clearButton.setText("CLEAR");
        clearButton.addActionListener(new TextButtonListener());
       
        //TextButtons to store the letters
//        textButton1.setText("A");
        textButton1.addActionListener(new TextButtonListener());
        //textButton2.setText("X");
        textButton2.addActionListener(new TextButtonListener());
//        textButton3.setText("A");
        textButton3.addActionListener(new TextButtonListener());
//        textButton4.setText("X");
        textButton4.addActionListener(new TextButtonListener());
//        textButton5.setText("X");
        textButton5.addActionListener(new TextButtonListener());
//        textButton6.setText("A");
        textButton6.addActionListener(new TextButtonListener());
//        textButton7.setText("X");
        textButton7.addActionListener(new TextButtonListener());        
//        textButton8.setText("A");
        textButton8.addActionListener(new TextButtonListener());
//        textButton9.setText("X");
        textButton9.addActionListener(new TextButtonListener());
//        textButton10.setText("A");
        textButton10.addActionListener(new TextButtonListener());
      
        //Layout of the buttons 
        javax.swing.GroupLayout textButtonPanelLayout = new javax.swing.GroupLayout(textButtonPanel);
        textButtonPanel.setLayout(textButtonPanelLayout);
        textButtonPanelLayout.setHorizontalGroup(
            textButtonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(textButtonPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(textButtonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(textButtonPanelLayout.createSequentialGroup()
                        .addComponent(textButton6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textButton7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textButton8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textButton9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textButton10))
                    .addGroup(textButtonPanelLayout.createSequentialGroup()
                        .addComponent(textButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textButton4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textButton5)))
                .addContainerGap(28, Short.MAX_VALUE))
        );
        textButtonPanelLayout.setVerticalGroup(
            textButtonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(textButtonPanelLayout.createSequentialGroup()
                .addGroup(textButtonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(textButtonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 18, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 64, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(titleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(157, 157, 157))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(picturePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(62, 62, 62))))
            .addGroup(layout.createSequentialGroup()
                .addGap(99, 99, 99)
                .addComponent(textfieldPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(textButtonPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(submitButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(clearButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(94, 94, 94))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(titleLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(picturePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(textfieldPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(submitButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(clearButton))
                    .addComponent(textButtonPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
    }// </editor-fold>   
    
    //------------   Creating and drawing IMAGE ---------------//
    //NEXT BUTTON LISTENER
	private class NextButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			setImage(nextButtonCount);
			setButtonText();
		}
	}

	private ImageIcon checkImageSize(ImageIcon icon){
		Image image = icon.getImage(); 
		if(icon.getIconWidth() > pictureLabel.getWidth()){
			image = image.getScaledInstance(pictureLabel.getWidth(), 
					pictureLabel.getHeight(), java.awt.Image.SCALE_SMOOTH);
		}
		icon = new ImageIcon(image);
		return icon;
	}
	/**
	 * Changing of Image
	 * @param count  the image to be changed
	 * */
	private void setImage(int count){
		String [] imageLinks = chooseImage("rain");
		ImageIcon icon = new ImageIcon(imageLinks[count]);
		nextButtonCount ++; 
		scoreCounter ++;
//		System.out.println(nextButtonCount);
		if(count >= imageLinks.length - 1 ){
//			String msg = "Finish! \nYour Score is " + score;
//			JOptionPane.showMessageDialog(null, msg);
			score = 0;
			nextButtonCount = 0;
		}
		clearTextField();
		pictureLabel.setIcon(checkImageSize(icon));
	}
	private String [] chooseImage(String in){
		String [] rain = {"images/rain2.jpg", "images/rain3.jpg" , "images/rain1.jpg"};
		String [] cold = {"images/cold1.jpg", "images/cold2.jpg" , "images/cold3.jpg"};
		if(in.equalsIgnoreCase("cold")){
			return cold;
		}else
			return rain;
	}
	//-------------  TEXT BUTTON ------------------//
	private class TextButtonListener implements ActionListener{
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String msg = arg0.getActionCommand();
				
				if(msg.equalsIgnoreCase("submit")){
					StringBuilder sb = new StringBuilder();
					sb.append(textfield1.getText());
					sb.append(textfield2.getText());
					sb.append(textfield3.getText());
					sb.append(textfield4.getText());
					
					// check answers
					if(rain.equalsIgnoreCase(sb.toString())){ 
						if(scoreCounter == 1){
							msg = "You have achieved 10 points!";
							score += 10;
						}else if(scoreCounter == 2){
							msg = "You have achieved 7 points!";
							score += 7;
						}
						else if(scoreCounter == 3){
							msg = "You have achieved 5 points!";
							score += 5;
						}
						scoreCounter = 0;
						JOptionPane.showMessageDialog(null, "Correct! " + msg  );
						setButtonText();
						setImage(nextButtonCount);
					}else
						JOptionPane.showMessageDialog(null, "Wrong! Try again! ");
					
				}else if(msg.equalsIgnoreCase("clear")){
					clearTextField();
					
				}else if(textfield1.getText().isEmpty()){
					textfield1.setText(msg);
				}else if(textfield2.getText().isEmpty()){
		    		textfield2.setText(msg);
		    	}else if(textfield3.getText().isEmpty()){
		    		textfield3.setText(msg);
		    	}else if(textfield4.getText().isEmpty()){
		    		textfield4.setText(msg);
		    	}
		    	
			}	
		}// end of private class button
	
	private void setButtonText(){
		String[] word = new WordShuffler().breakWord(rain);
        for(int i = 0; i < buttonList.size(); i++ ){
        	buttonList.get(i).setText(word[i]);
//        	System.out.println("Button " + i + " is " + word[i]);
        }
	}
    //---------- TEXT FIELD -----------//

    private void clearTextField(){
    	textfield1.setText("");
		textfield2.setText("");
		textfield3.setText("");
		textfield4.setText("");
    }

    

                   
}

