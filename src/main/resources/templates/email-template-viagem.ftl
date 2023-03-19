<head>
    <meta charset="UTF-8">
    <title>Heroes Logistica</title>
</head>

<body>
    <div align=center style="background-color: #d12121;">
        <br>
        <img src="/images/logo2.png" alt="Logo" height="150px" width="150px">
        <h1 style="color: #f2f2f2; font-family:courier;"> Heroes Logística </h1>
        <br>
    </div>

    <div style="background-color: #f2f2f2; padding: 20px;">
        <h2 align=center>Olá, ${nomeUsuario}!</h2>
        <br>
        <br>${mensagem}
        <br>
        <br>O local de partida é: <b>${rota.getLocalPartida()}</b>
        <br>O local do destino é: <b>${rota.getLocalDestino()}</b>
        <br>
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

    <div align=center style="background-color: #d12121;">
        <br>
        <h1 style="color: #f2f2f2; font-family:courier;">"Voamos por você!"</h1>
        <br>
    </div>

</body>
