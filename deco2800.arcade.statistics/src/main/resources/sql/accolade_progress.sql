SELECT Accolades.accolade_id, 
		SUM(Player_Accolades.score),
		Accolades.name,
		Accolades.string,
		Accolades.unit,
		Accolades.modifier,
		Accolades.tag,
		Accolades.image
FROM Accolades, Player_Accolades
WHERE Accolades.accolade_id = Player_Accolades.accolade_id
GROUP BY Accolades.accolade_id;

