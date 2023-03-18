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
        <h1 style="color: #f2f2f2;">DBC Logistica</h1>
        <br>
    </div>

    <h2>Olá, ${nomeUsuario}!</h2>
    <p>${mensagem} </p>
    <p>O local de partida é:  ${rota.getLocalPartida()}</p>
    <p>O local do destino é:  ${rota.getLocalDestino()}</p>
    <br>BOA VIAGEM!<br> <br>
    <br>Atenciosamente,<br>
    <br><b>${nome}</b>
</div>
</body>
</html>

