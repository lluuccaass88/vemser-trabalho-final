<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>DBC Logistica</title>
</head>
<body>
<div style="background-color: #f2f2f2; padding: 20px;">
    <div style="background-color: #dc1e1e;">
        <br>
        <h1 align=center style="color: #f2f2f2;">DBC Logística</h1>
        <br>
    </div>

    <h2 align=center>Olá, ${nomeUsuario}!</h2>
    <div>
        <br>${mensagem}
        <br>
        <br>O local de partida é: <b>${rota.getLocalPartida()}</b>
        <br>O local do destino é: <b>${rota.getLocalDestino()}</b>
        <br>
        <br>
        <br>Qualquer duvida é só contatar o suporte pelo email ->  ${emailContato}
        <br>
        <br><b>BOA VIAGEM!</b>
        <br>
        <br>
        <br>Atenciosamente,
        <br><b>${nome}</b>
    </div>
</div>
</body>
</html>
