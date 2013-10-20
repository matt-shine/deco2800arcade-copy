package deco2800.arcade.arcadeui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import deco2800.arcade.client.ArcadeInputMux;
import deco2800.arcade.client.ArcadeSystem;
import deco2800.arcade.protocol.lobby.ActiveMatchDetails;

import java.util.*;

/**
 * Match Me Multiplayer join Screen 
 * @author Kieran Burke
 * 
 */

public class JoinMatchList implements Screen {

	private class FrontPageStage extends Stage {
	}

	private Skin skin;
	private FrontPageStage stage;

	public boolean multiplayerEnabled;
	private boolean bclicked;

	Texture bg;
	Sprite bgSprite;
	SpriteBatch batch;

	private ArcadeUI arcadeUI;

	ArrayList<ActiveMatchDetails> matches;
	
/**
 * Main constructor that creates all on screen GUI elements  
 * @param ui Get Arcade screen details 
 * 
 */

	public JoinMatchList(ArcadeUI ui) {

		arcadeUI = ui;
		skin = new Skin(Gdx.files.internal("loginSkin.json"));
		skin.add("background", new Texture("homescreen_bg.png"));
		stage = new FrontPageStage();

		Table table = new Table();
		table.setFillParent(true);
		table.setBackground(skin.getDrawable("background"));
		stage.addActor(table);

		bg = new Texture("homescreen_bg.png");
		bg.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		bgSprite = new Sprite(bg);
		batch = new SpriteBatch();

		// Text Buttons
		final TextButton chessButton = new TextButton("Chess", skin, "green");

		final TextButton chatButton = new TextButton("Chat", skin);
		final TextButton towerButton = new TextButton("Tower Defence", skin,
				"magenta");
		final TextButton pongButton = new TextButton("Pong", skin, "blue");

		final TextButton chessButton2 = new TextButton("Chess", skin, "green");
		final TextButton towerButton2 = new TextButton("Tower Defence", skin,
				"magenta");
		final TextButton pongButton2 = new TextButton("Pong", skin, "blue");

		final TextButton raidenButton = new TextButton("Raiden", skin, "green");
		final TextButton snakeButton = new TextButton("Snakes & Ladders", skin,
				"magenta");
		final TextButton tictacButton = new TextButton("TicTacToe", skin,
				"blue");

		final TextButton burnButton = new TextButton("Burning Skies", skin,
				"green");
		final TextButton checkersButton = new TextButton("Checkers", skin,
				"magenta");
		final TextButton connect4Button = new TextButton("Connect 4", skin,
				"blue");

		final TextButton pacmanButton = new TextButton("Pacman", skin, "green");
		final TextButton deerButton = new TextButton("Deer Forest", skin,
				"magenta");
		final TextButton jungleButton = new TextButton("Jungle Jump", skin,
				"blue");

		final TextButton mixmazeButton = new TextButton("Mix Maze", skin,
				"green");
		final TextButton landButton = new TextButton("Land Invaders", skin,
				"magenta");

		final int bWidth = 300;
		final int bHeight = 300;
		final int bX = 150;
		final int bY = 220;
		final int enlarge = 50;
		final int bX2 = bX + bWidth + (enlarge);
		final int bX3 = bX + 2 * (bWidth + enlarge);

		final Table listtable = new Table();
		listtable.setFillParent(true);
		stage.addActor(listtable);
		
		final Table table2 = new Table();
		table2 .setFillParent(true);
		stage.addActor(table2);

		Label title = new Label("Choose a Game...", skin);
		TextButton button3 = new TextButton("Return to Lobby", skin);
		TextButton button4 = new TextButton("<", skin);
		TextButton button5 = new TextButton(">", skin);

		table2.add(title).width(80).height(40).padRight(180).padBottom(630);
		table.add(button4).width(60).height(40).padTop(10).padLeft(310);
		table.add(button3).width(300).height(40).padTop(600).padRight(420)
				.padLeft(390);
		table.add(button5).width(60).height(40).padTop(10).padRight(290);

		/* <--SCROLLER START--> */

		// Scroll Right
		button5.addListener(new ChangeListener() {
			public void changed(ChangeEvent event, Actor actor) {

				if (chessButton.getStage() != null
						|| chessButton2.getStage() != null) {

					listtable.clear();

					raidenButton.addListener((new ClickListener() {
						public void enter(InputEvent event, float x, float y,
								int pointer, Actor fromActor) {

							
							raidenButton.setText("Raiden");
						}

						public void exit(InputEvent event, float x, float y,
								int pointer, Actor fromActor) {
							
							raidenButton.setText("Raiden");
						}
					}));

					raidenButton.addListener((new ChangeListener() {
						public void changed(ChangeEvent event, Actor actor) {
							// TODO
						}
					}));

					snakeButton.addListener((new ClickListener() {
						public void enter(InputEvent event, float x, float y,
								int pointer, Actor fromActor) {

							
							snakeButton.setText("Snakes & Ladders");
						}

						public void exit(InputEvent event, float x, float y,
								int pointer, Actor fromActor) {
							
							snakeButton.setText("Snakes & Ladders");
						}
					}));

					snakeButton.addListener((new ChangeListener() {
						public void changed(ChangeEvent event, Actor actor) {
							// TODO
						}
					}));

					tictacButton.addListener((new ClickListener() {
						public void enter(InputEvent event, float x, float y,
								int pointer, Actor fromActor) {

							
							tictacButton.setText("TicTacToe");
						}

						public void exit(InputEvent event, float x, float y,
								int pointer, Actor fromActor) {
							
							tictacButton.setText("TicTacToe");
						}
					}));

					tictacButton.addListener((new ChangeListener() {
						public void changed(ChangeEvent event, Actor actor) {
							// TODO
						}
					}));

					listtable.add(raidenButton).width(300).height(300);
					listtable.add(snakeButton).width(300).height(300);
					listtable.add(tictacButton).width(300).height(300);

				}

				else if (raidenButton.getStage() != null) {

					listtable.clear();

					burnButton.addListener((new ClickListener() {
						public void enter(InputEvent event, float x, float y,
								int pointer, Actor fromActor) {

							
							burnButton.setText("Burning Skies");
						}

						public void exit(InputEvent event, float x, float y,
								int pointer, Actor fromActor) {
							
							burnButton.setText("Burning Skies");
						}
					}));

					burnButton.addListener((new ChangeListener() {
						public void changed(ChangeEvent event, Actor actor) {
							// TODO
						}
					}));

					checkersButton.addListener((new ClickListener() {
						public void enter(InputEvent event, float x, float y,
								int pointer, Actor fromActor) {

							
							checkersButton.setText("Checkers");
						}

						public void exit(InputEvent event, float x, float y,
								int pointer, Actor fromActor) {
							
							checkersButton.setText("Checkers");
						}
					}));

					checkersButton.addListener((new ChangeListener() {
						public void changed(ChangeEvent event, Actor actor) {
							// TODO
						}
					}));

					connect4Button.addListener((new ClickListener() {
						public void enter(InputEvent event, float x, float y,
								int pointer, Actor fromActor) {

							
							connect4Button.setText("Connect 4");
						}

						public void exit(InputEvent event, float x, float y,
								int pointer, Actor fromActor) {
							
							connect4Button.setText("Connect 4");
						}
					}));

					connect4Button.addListener((new ChangeListener() {
						public void changed(ChangeEvent event, Actor actor) {
							// TODO
						}
					}));

					listtable.add(burnButton).width(300).height(300);
					listtable.add(checkersButton).width(300).height(300);
					listtable.add(connect4Button).width(300).height(300);

				}

				else if (burnButton.getStage() != null) {

					listtable.clear();

					pacmanButton.addListener((new ClickListener() {
						public void enter(InputEvent event, float x, float y,
								int pointer, Actor fromActor) {

							
							pacmanButton.setText("Pacman");
						}

						public void exit(InputEvent event, float x, float y,
								int pointer, Actor fromActor) {
							
							pacmanButton.setText("Pacman");
						}
					}));

					pacmanButton.addListener((new ChangeListener() {
						public void changed(ChangeEvent event, Actor actor) {
							// TODO
						}
					}));

					deerButton.addListener((new ClickListener() {
						public void enter(InputEvent event, float x, float y,
								int pointer, Actor fromActor) {

							
							deerButton.setText("Deer Jungle");
						}

						public void exit(InputEvent event, float x, float y,
								int pointer, Actor fromActor) {
							
							deerButton.setText("Deer Jungle");
						}
					}));

					deerButton.addListener((new ChangeListener() {
						public void changed(ChangeEvent event, Actor actor) {
							// TODO
						}
					}));

					jungleButton.addListener((new ClickListener() {
						public void enter(InputEvent event, float x, float y,
								int pointer, Actor fromActor) {

							
							jungleButton.setText("Jungle Jump");
						}

						public void exit(InputEvent event, float x, float y,
								int pointer, Actor fromActor) {
							
							jungleButton.setText("Jungle Jump");
						}
					}));

					jungleButton.addListener((new ChangeListener() {
						public void changed(ChangeEvent event, Actor actor) {
							// TODO
						}
					}));

					listtable.add(pacmanButton).width(300).height(300);
					listtable.add(deerButton).width(300).height(300);
					listtable.add(jungleButton).width(300).height(300);

				}

				else if (pacmanButton.getStage() != null) {

					listtable.clear();

					mixmazeButton.addListener((new ClickListener() {
						public void enter(InputEvent event, float x, float y,
								int pointer, Actor fromActor) {

							
							mixmazeButton.setText("Mix Maze");
						}

						public void exit(InputEvent event, float x, float y,
								int pointer, Actor fromActor) {
							
							mixmazeButton.setText("Mix Maze");
						}
					}));

					mixmazeButton.addListener((new ChangeListener() {
						public void changed(ChangeEvent event, Actor actor) {
							// TODO
						}
					}));

					landButton.addListener((new ClickListener() {
						public void enter(InputEvent event, float x, float y,
								int pointer, Actor fromActor) {

							
							landButton.setText("Land Invaders");
						}

						public void exit(InputEvent event, float x, float y,
								int pointer, Actor fromActor) {
							
							landButton.setText("Land Invaders");
						}
					}));

					landButton.addListener((new ChangeListener() {
						public void changed(ChangeEvent event, Actor actor) {
							// TODO
						}
					}));

					listtable.add(mixmazeButton).width(300).height(300);
					listtable.add(landButton).width(300).height(300);

				}
			}
		});

		// Scroll Left
		button4.addListener(new ChangeListener() {
			public void changed(ChangeEvent event, Actor actor) {

				if (raidenButton.getStage() != null) {

					listtable.clear();

					pongButton2.addListener((new ClickListener() {
						public void enter(InputEvent event, float x, float y,
								int pointer, Actor fromActor) {
							
							pongButton2.setText("Pong");

						}

						public void exit(InputEvent event, float x, float y,
								int pointer, Actor fromActor) {
							
							pongButton2.setText("Pong");
						}
					}));

					// AFTER SCROLLED
					pongButton2.addListener((new ChangeListener() {
						public void changed(ChangeEvent event, Actor actor) {
							dispose();
							ArcadeSystem.setMatchMaking(true);
							ArcadeSystem.setMultiplayerEnabled(true);
							ArcadeSystem.setGameWaiting(true);
							arcadeUI.setScreen(arcadeUI.getWait());
							ArcadeSystem.goToGame("Pong");
						}
					}));

					towerButton2.addListener((new ClickListener() {
						public void enter(InputEvent event, float x, float y,
								int pointer, Actor fromActor) {
							
							towerButton2.setText("Tower Defence");

						}

						public void exit(InputEvent event, float x, float y,
								int pointer, Actor fromActor) {
							
							towerButton2.setText("Tower Defence");
						}
					}));

					towerButton2.addListener((new ChangeListener() {
						public void changed(ChangeEvent event, Actor actor) {
							// TODO
						}
					}));

					chessButton2.addListener((new ClickListener() {
						public void enter(InputEvent event, float x, float y,
								int pointer, Actor fromActor) {

							
							chessButton2.setText("Chess");
						}

						public void exit(InputEvent event, float x, float y,
								int pointer, Actor fromActor) {
							
							chessButton2.setText("Chess");
						}
					}));

					chessButton2.addListener((new ChangeListener() {
						public void changed(ChangeEvent event, Actor actor) {
							// TODO
						}
					}));

					listtable.add(pongButton2).width(300).height(300);
					listtable.add(towerButton2).width(300).height(300);
					listtable.add(chessButton2).width(300).height(300);

				}

				else if (burnButton.getStage() != null) {

					listtable.clear();

					raidenButton.addListener((new ClickListener() {
						public void enter(InputEvent event, float x, float y,
								int pointer, Actor fromActor) {

							
							raidenButton.setText("Raiden");
						}

						public void exit(InputEvent event, float x, float y,
								int pointer, Actor fromActor) {
							
							raidenButton.setText("Raiden");
						}
					}));

					raidenButton.addListener((new ChangeListener() {
						public void changed(ChangeEvent event, Actor actor) {
							// TODO
						}
					}));

					snakeButton.addListener((new ClickListener() {
						public void enter(InputEvent event, float x, float y,
								int pointer, Actor fromActor) {

							
							snakeButton.setText("Snakes & Ladders");
						}

						public void exit(InputEvent event, float x, float y,
								int pointer, Actor fromActor) {
							
							snakeButton.setText("Snakes & Ladders");
						}
					}));

					snakeButton.addListener((new ChangeListener() {
						public void changed(ChangeEvent event, Actor actor) {
							// TODO
						}
					}));

					tictacButton.addListener((new ClickListener() {
						public void enter(InputEvent event, float x, float y,
								int pointer, Actor fromActor) {

							
							tictacButton.setText("TicTacToe");
						}

						public void exit(InputEvent event, float x, float y,
								int pointer, Actor fromActor) {
							
							tictacButton.setText("TicTacToe");
						}
					}));

					tictacButton.addListener((new ChangeListener() {
						public void changed(ChangeEvent event, Actor actor) {
							// TODO
						}
					}));

					listtable.add(raidenButton).width(300).height(300);
					listtable.add(snakeButton).width(300).height(300);
					listtable.add(tictacButton).width(300).height(300);

				}

				else if (mixmazeButton.getStage() != null) {

					listtable.clear();

					pacmanButton.addListener((new ClickListener() {
						public void enter(InputEvent event, float x, float y,
								int pointer, Actor fromActor) {

							
							pacmanButton.setText("Pacman");
						}

						public void exit(InputEvent event, float x, float y,
								int pointer, Actor fromActor) {
							
							pacmanButton.setText("Pacman");
						}
					}));

					pacmanButton.addListener((new ChangeListener() {
						public void changed(ChangeEvent event, Actor actor) {
							// TODO
						}
					}));

					deerButton.addListener((new ClickListener() {
						public void enter(InputEvent event, float x, float y,
								int pointer, Actor fromActor) {

							
							deerButton.setText("Deer Jungle");
						}

						public void exit(InputEvent event, float x, float y,
								int pointer, Actor fromActor) {
							
							deerButton.setText("Deer Jungle");
						}
					}));

					deerButton.addListener((new ChangeListener() {
						public void changed(ChangeEvent event, Actor actor) {
							// TODO
						}
					}));

					jungleButton.addListener((new ClickListener() {
						public void enter(InputEvent event, float x, float y,
								int pointer, Actor fromActor) {

							
							jungleButton.setText("Jungle Jump");
						}

						public void exit(InputEvent event, float x, float y,
								int pointer, Actor fromActor) {
							
							jungleButton.setText("Jungle Jump");
						}
					}));

					jungleButton.addListener((new ChangeListener() {
						public void changed(ChangeEvent event, Actor actor) {
							// TODO
						}
					}));

					listtable.add(pacmanButton).width(300).height(300);
					listtable.add(deerButton).width(300).height(300);
					listtable.add(jungleButton).width(300).height(300);

				}

				else if (pacmanButton.getStage() != null) {

					listtable.clear();

					burnButton.addListener((new ClickListener() {
						public void enter(InputEvent event, float x, float y,
								int pointer, Actor fromActor) {

							
							burnButton.setText("Burning Skies");
						}

						public void exit(InputEvent event, float x, float y,
								int pointer, Actor fromActor) {
							
							burnButton.setText("Burning Skies");
						}
					}));

					burnButton.addListener((new ChangeListener() {
						public void changed(ChangeEvent event, Actor actor) {
							// TODO
						}
					}));

					checkersButton.addListener((new ClickListener() {
						public void enter(InputEvent event, float x, float y,
								int pointer, Actor fromActor) {

							
							checkersButton.setText("Checkers");
						}

						public void exit(InputEvent event, float x, float y,
								int pointer, Actor fromActor) {
							
							checkersButton.setText("Checkers");
						}
					}));

					checkersButton.addListener((new ChangeListener() {
						public void changed(ChangeEvent event, Actor actor) {
							// TODO
						}
					}));

					connect4Button.addListener((new ClickListener() {
						public void enter(InputEvent event, float x, float y,
								int pointer, Actor fromActor) {

							
							connect4Button.setText("Connect 4");
						}

						public void exit(InputEvent event, float x, float y,
								int pointer, Actor fromActor) {
							
							connect4Button.setText("Connect 4");
						}
					}));

					connect4Button.addListener((new ChangeListener() {
						public void changed(ChangeEvent event, Actor actor) {
							// TODO
						}
					}));

					listtable.add(burnButton).width(300).height(300);
					listtable.add(checkersButton).width(300).height(300);
					listtable.add(connect4Button).width(300).height(300);

				}

			}
		});

		/* <--SCROLLER END--> */

		// Return to lobby event listener
		button3.addListener(new ChangeListener() {
			public void changed(ChangeEvent event, Actor actor) {

				ArcadeSystem.setMatchMaking(false);
				ArcadeSystem.setMultiplayerEnabled(true);
				arcadeUI.setScreen(arcadeUI.getLobby());

			}
		});

		pongButton.addListener((new ClickListener() {
			public void enter(InputEvent event, float x, float y, int pointer,
					Actor fromActor) {
				
				pongButton.setText("Pong");

			}

			public void exit(InputEvent event, float x, float y, int pointer,
					Actor fromActor) {
				
				pongButton.setText("Pong");
			}
		}));

		// SCROLLHERE
		pongButton.addListener((new ChangeListener() {
			public void changed(ChangeEvent event, Actor actor) {
				// ArcadeSystem.login("pong");
				// bclicked = true;
				dispose();
				ArcadeSystem.setMatchMaking(true);
				ArcadeSystem.setMultiplayerEnabled(true);
				ArcadeSystem.setGameWaiting(true);
				arcadeUI.setScreen(arcadeUI.getWait());
				ArcadeSystem.goToGame("Pong");
			}
		}));

		towerButton.addListener((new ClickListener() {
			public void enter(InputEvent event, float x, float y, int pointer,
					Actor fromActor) {
				
				towerButton.setText("Tower Defence");

			}

			public void exit(InputEvent event, float x, float y, int pointer,
					Actor fromActor) {
				
				towerButton.setText("Tower Defence");
			}
		}));

		towerButton.addListener((new ChangeListener() {
			public void changed(ChangeEvent event, Actor actor) {
				// TODO
			}
		}));

		chessButton.addListener((new ClickListener() {
			public void enter(InputEvent event, float x, float y, int pointer,
					Actor fromActor) {

				
				chessButton.setText("Chess");
			}

			public void exit(InputEvent event, float x, float y, int pointer,
					Actor fromActor) {
				
				chessButton.setText("Chess");
			}
		}));

		chessButton.addListener((new ChangeListener() {
			public void changed(ChangeEvent event, Actor actor) {
				// TODO
			}
		}));

		listtable.add(pongButton).width(300).height(300);
		listtable.add(towerButton).width(300).height(300);
		listtable.add(chessButton).width(300).height(300);
		listtable.row();

	}

	@Override
	public void show() {
		ArcadeInputMux.getInstance().addProcessor(stage);
	}

	@Override
	public void render(float arg0) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		batch.begin();
		bgSprite.draw(batch);
		batch.end();

		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
		

	}

	@Override
	public void dispose() {
		ArcadeInputMux.getInstance().removeProcessor(stage);
		stage.dispose();
		skin.dispose();
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
	public void resize(int arg0, int arg1) {
	}
}