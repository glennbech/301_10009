[![Build Status](https://travis-ci.com/M99/PGR301_10009.svg?token=9rUAuyK5rhXyo46pbgfm&branch=main)](https://travis-ci.com/M99/PGR301_10009)

## Docker/Travis/Google Container Registry
1. Google Cloud
    - Et prosjekt må opprettes og Container Registry må aktiveres.
2. Service Account
    - For å bygge og pushe et Docker Image til GCS må det opprettes en Service Account. Denne trenger rollen Storage Admin.
    - En .json nøkkel for Service Accounten må lastes ned/lages og bli navngitt `google-key.json`. Denne skal plasseres i rot-mappen.
3. Kryptering
    - Etter å ha logget inn via Travis CLI, må følgende kommandoer kjøres.
    ```
    travis encrypt GCP_PROJECT_ID=<project-id> --add env.global
    travis encrypt IMAGE=<host-name>/<project-id>/<image-name> --add env.global
    travis encrypt LOGZ_TOKEN=<logzioToken> --add env.global
    travis encrypt LOGZ_URL=<logzioUrl> --add env.global
    travis encrypt-file google-key.json
   ```
4. Github
    - Ved commits til main/master vil et Docker Image bli bygget og pushet til Google Container Registry.

## Metrics
Ved bruk av Micrometer og InfluxDB blir forskjellig statistikk innsamlet og lagret slik at de kan vises i Grafana.
I grafana-mappen befinner det seg en .json fil som kan importeres til Grafana for å bruke et ferdig dashboard.
####Counter
* `Games Purchased`: Antall spill som har blitt kjøpt/lagt til
* `Games Sold`: Antall spill som har blitt solgt/slettet

####Gauge
* `Owned Games`: Antall spill som har blitt kjøpt/lagt til, minus antall spill som har blitt solgt/slettet

####Distribution Summary
* `Game Prices (purchased)`: Oversikt over prisene på spillene som har blitt kjøpt/lagt til

####Timer
* `Processing Time (game purchasing)`: Antall millisekunder det tok å prosessere metoden for et spillkjøp

####LongTaskTimer
* `Processing Time (game selling)`: Antall millisekunder det tok å prosessere og finne en kjøper for et spill

## Logging
Logging foregår ved hjelp av Logz.io, og skjer når et av de tre endepunktene blir aksessert. Informasjonen som blir logget er avhengig av om responsen er sukessfull eller ikke.