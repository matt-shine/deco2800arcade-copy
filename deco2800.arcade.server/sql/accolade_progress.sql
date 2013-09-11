CREATE OR REPLACE VIEW [acolade_progress] AS
SELECT Game_Accolades.accolade_id, 
		SUM(Player_Accolades.score),
		Game_Accolades.name,
		Game_Accolades.string,
		Game_Accolades.unit,
		Game_Accolades.modifier,
		Game_Accolades.tag,
		Game_Accolades.image
FROM Game_Accolades, Player_Accolades
WHERE Game_Accolades.accolade_id = Player_Accolades.accolade_id
ORDER BY Game_Accolades.game_id

