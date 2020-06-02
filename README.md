# Semestrální práce z předmětu KIV/IR

## Zadání
Cílem semestrální práce je naučit se implementovat komplexní systém s využitím hotových knihoven pro preprocessing.
Vedlejším produktem bude hlubší porozumnění indexerům, vyhledávacím systémům a přednáškám.Systém po předchozím 
předzpracování zaindexuje zadané dokumenty a poté umožní vyhledávání nad vytvořeným indexem. Vyhledávání je možné 
zadáním dotazu s logickými operátory AND, OR, NOT a s použitím závorek. Výsledek dotazu by měl vrátit top x (např. 10) 
relevantních dokumentů seřazených dle relevance.

 
## Nadstandardní funkčnosti
- File-base index
- Pozdější doindexování nového dokumentu
- Webové rozhraní
- Dokumentace v TEXu

## Spuštění v dockeru
* přesunout se do root adresáře projektu (tam kde je soubor docker-compose.yml)
* spustit následující příkazy (na některých systémech bude zapotřebí spustit pod super uživatelem su ...)
    * ```docker-compose pull``` - stáhnutí existujích image z docker repository [backend image](https://hub.docker.com/r/ondrejvane/ir-backend) a [frontend image](https://hub.docker.com/r/ondrejvane/ir-frontend)
    * ```docker-compose up``` - spuštění příslušným image (aplikace nastartuje na adrese localhost:4200)

## Struktura projektu
* BE/KIV-IR - kořenový adresář pro backend projektu, zde je jádro vyhledávání (package core)
* FE/KIV-IR - kořenový adresář pro frontend projektu, aplikace je napsaná v Angularu a komunikuje s backendem pomocí JSON
* docker-compose.yml - soubor pro spuštění celé aplikace v dockeru viz příkazy níže
  
## Výsledky evaluačního skriptu
- num_q          	all	50
- num_ret        	all	1737264
- num_rel        	all	762
- num_rel_ret    	all	749
- map            	all	0.2385
- gm_ap          	all	0.1077
- R-prec         	all	0.2478
- bpref          	all	0.2369
- recip_rank     	all	0.4187
- ircl_prn.0.00  	all	0.4646
- ircl_prn.0.10  	all	0.4177
- ircl_prn.0.20  	all	0.3620
- ircl_prn.0.30  	all	0.3115
- ircl_prn.0.40  	all	0.2947
- ircl_prn.0.50  	all	0.2628
- ircl_prn.0.60  	all	0.2211
- ircl_prn.0.70  	all	0.1846
- ircl_prn.0.80  	all	0.1446
- ircl_prn.0.90  	all	0.0869
- ircl_prn.1.00  	all	0.0652
- P5             	all	0.2680
- P10            	all	0.2460
- P15            	all	0.2253
- P20            	all	0.1950
- P30            	all	0.1573
- P100           	all	0.0786
- P200           	all	0.0490
- P500           	all	0.0241
- P1000          	all	0.0133
