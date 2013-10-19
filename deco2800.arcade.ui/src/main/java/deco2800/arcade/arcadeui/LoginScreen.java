package deco2800.arcade.arcadeui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.tablelayout.BaseTableLayout;
import deco2800.arcade.client.Arcade;
import deco2800.arcade.client.ArcadeInputMux;
import deco2800.arcade.client.ArcadeSystem;
import deco2800.arcade.client.GameOverListener;
import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.client.network.NetworkException;
import deco2800.arcade.client.network.listener.NetworkListener;
import deco2800.arcade.protocol.connect.ConnectionResponse;
import deco2800.arcade.client.ArcadeInputMux;
import deco2800.arcade.client.ArcadeSystem;
import deco2800.arcade.arcadeui.FrontPage;


public class LoginScreen implements Screen {

    private class LoginScreenStage extends Stage {}

    private Skin skin;
    private LoginScreenStage stage;
    private static ArcadeUI arcadeUI;
    

    public LoginScreen(ArcadeUI ui) {
        arcadeUI = ui;

        skin = new Skin(Gdx.files.internal("loginSkin.json"));
        skin.add("background", new Texture("homescreen_bg.png"));

        stage = new LoginScreenStage();

        Table table = new Table();
        table.setFillParent(true);
        table.setBackground(skin.getDrawable("background"));
        stage.addActor(table);

        final Label tempLabel = new Label("To access the multiplayer lobby\nlogin with username:multi\nTo proceed as normal\nlogin with any username:admin password:admin", skin);  // Temporary label to display a message
        tempLabel.setAlignment(Align.center);
        final Label errorLabel = new Label("", skin, "error");
        errorLabel.setAlignment(Align.center);
        final TextField usernameText = new TextField("", skin);
        usernameText.setMessageText("Username");
        final TextField passwordText = new TextField("", skin);
        passwordText.setMessageText("Password");
        passwordText.setPasswordMode(true);
        passwordText.setPasswordCharacter('*');
        final TextField serverText = new TextField("", skin);
        serverText.setMessageText("Server") ;
        CheckBox rememberBox = new CheckBox("Remember Me", skin);
        rememberBox.getCells().get(0).size(25, 25);
        rememberBox.getCells().get(0).pad(5);
        rememberBox.getCells().get(1).pad(2);
        TextButton loginButton = new TextButton("Login", skin);
        TextButton registerButton = new TextButton("Register", skin, "default-blue");
        TextButton forgotLogButton = new TextButton("Forgot Login?", skin, "default-red");

        table.add(tempLabel).colspan(2);  // Temporary label to display a message
        table.row();
        table.add(errorLabel).width(400).pad(5).colspan(2);
        table.row();
        table.add(usernameText).width(400).pad(5).colspan(2);
        table.row();
        table.add(passwordText).width(400).pad(5).colspan(2);
        table.row();
        table.add(serverText).width(400).pad(5).colspan(2);
        table.row();
        table.add(rememberBox).width(190).height(25).pad(5).colspan(2).align(BaseTableLayout.LEFT);
        table.row();
        table.add(loginButton).width(190).height(50).pad(5);
        table.add(registerButton).width(190).height(50).pad(5);
        table.row();
        table.add(forgotLogButton).width(400).height(35).pad(5).colspan(2);

        
        class ConnectionListener extends NetworkListener {

                @Override
                public void connected(Connection connection) {
                        super.connected(connection);
                }

                @Override
                public void disconnected(Connection connection) {
                        super.disconnected(connection);
                }

                @Override
                public void idle(Connection connection) {
                        super.idle(connection);
                }

                        @Override
                        public void received(Connection connection, Object object) {
                                super.received(connection, object);

                                if (object instanceof ConnectionResponse) {

                                        ConnectionResponse connectionResponse = (ConnectionResponse) object;
                                        if (connectionResponse.register) {
                                                errorLabel.setText("User registered!");
                                                arcadeUI.setScreen(arcadeUI.login);
                                        } else {
                                                if (connectionResponse.playerID >= 0) {
                                                        FrontPage.setName(usernameText.getText());
                                                        arcadeUI.setScreen(arcadeUI.main);
                                                }
                                        }
                                        if (connectionResponse.playerID == -2) {
                                                errorLabel.setText("Error loggin in");
                                                arcadeUI.setScreen(arcadeUI.login);
                                        } else if (connectionResponse.playerID == -1) {
                                                errorLabel.setText("Incorrect password");
                                                arcadeUI.setScreen(arcadeUI.login);
                                        } else if (connectionResponse.playerID == -3) {
                                                errorLabel.setText("Cannot register user. User already exists.");
                                                arcadeUI.setScreen(arcadeUI.login);
                                        }
                                }
                        }
                
        }
        
        ConnectionListener listener = new ConnectionListener();
        ArcadeSystem.addListener(listener);        

        

        loginButton.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                if (usernameText.getText().equals("")) {
                    // no username entered, throw error
                    errorLabel.setText("No Username Supplied");
                }
                else if (usernameText.getText().toLowerCase().equals("multi")) {
                    // TEMPORARY
                    ArcadeSystem.login("debuguser");
                        arcadeUI.setScreen(arcadeUI.lobby);
                }
                else {
                    ArcadeSystem.login(usernameText.getText(), passwordText.getText());
                }
            }
        });

        registerButton.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                arcadeUI.setScreen(arcadeUI.register);
            }
        });

        forgotLogButton.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
            }
        });
    }


        public static void setUI(String input) {
                if (input.equals("home")){
                        arcadeUI.setScreen(arcadeUI.home);
                }
        }

    @Override
    public void render(float arg0) {
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
        ArcadeInputMux.getInstance().addProcessor(stage);
    }

    @Override
    public void hide() {
        ArcadeInputMux.getInstance().removeProcessor(stage);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}