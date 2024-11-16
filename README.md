# Telegram Currency and Crypto Bot

Это Telegram-бот, созданный с помощью Spring Boot, который предоставляет текущие курсы обмена валют и цены на криптовалюты. Бот поддерживает конвертацию валют и отображает последние курсы биткоина (BTC) и Эфириума (ETH).

## Features

- Получайте текущие курсы обмена USD, EUR, RUB и CNY.
- Конвертируйте валюты между USD, EUR, RUB и CNY.
- Получайте последние цены на Bitcoin (BTC) и Ethereum (ETH).
- Отображайте результат конвертации в сообщении.

## Prerequisites

- Java Development Kit (JDK) 23
- Gradle
- Telegram Bot API token

## Installation

1. Склонировать репозиторий:
    ```sh
    git clone https://github.com/Binarer/Currency-tg-bot.git
    cd currency-crypto-bot
    ```

2. Забилдить проект:
    ```sh
    gradle build
    ```

3. Run the application:
    ```sh
    gradle bootRun
    ```

4. Настройте Telegram-бота:
    - Создайте нового бота с помощью BotFather и получите токен API.
    - получите ключи с v6.exchangerate-api.com, api.freecryptoapi.com
    - Обновите файл "application.properties", указав имя пользователя и токен вашего бота, ключи от апишек:
        ```properties
        telegram.bot.username=YourBotUsername
        telegram.bot.token=YourBotToken
        currency.api.url=https://v6.exchangerate-api.com/v6/YOUR_KEY/latest/
        crypto.api.url=https://api.freecryptoapi.com/v1/getData?symbol=BTC+ETH+ETHBTC@binance
        crypto.api.key=YOUR_KEY
        ```

## Usage

1. Заведите чат с ботом в Telegram.
2. Для взаимодействия с ботом используйте следующие команды:
    - `/start`: Запустите бота и отобразите главное меню.
    - `/help`: Получите справочную информацию.
    - `/rate`: Получите текущие курсы валют для USD, EUR, RUB и CNY.
    - `/convert`: конвертируйте валюты между долларами США, евро, рублями и юанями.
    - `/crypto`: Получайте последние цены на биткоин (BTC) и Эфириум (ETH).
