<head>
    <meta charset="UTF-8">
    <title>TruckLog</title>
</head>

<body>
    <div align=center style="background-color: #94caba;">
        <br><br>
        <img src="https://github.com/lluuccaass88/vemser-trabalho-final/blob/main/images/truck-log-log.png?raw=true" alt="Logo" height="100px" width="150px">
        <br><br>
    </div>

    <div style="background-color: #f2f2f2; padding: 20px; font-family:courier;">
        <h2 align=center>Olá, ${nomeUsuario}!</h2>
        <br>
        <br>${mensagem}
        <br>
        <br>O local de partida é: <b>${rota.getLocalPartida()}</b>
        <br>Com a data inicial da viagem em: <b>${viagem.getDataInicio()}</b>
        <br>O local do destino é: <b>${rota.getLocalDestino()}</b>
        <br>Com a data prevista para o encerramento em: <b>${viagem.getDataFim()}</b>
        <br>Não se preocupe, qualquer mudança que venha ocorrer você será informado</b>
        <br>Qualquer duvida é só contatar o suporte pelo email ->  ${emailContato}
        <br>
        <br>
        <br><b>BOA VIAGEM!</b>
        <br>
        <br>
        <br>Atenciosamente,
        <br><b>${nome}</b>
        <br>
        <br>
    </div>

    <div align=center style="background-color: #94caba;">
        <br>
        <h1 style="color: #204844; font-family:courier;">"Voamos por você!"</h1>
        <br>
    </div>

</body>
