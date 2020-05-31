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

## Struktura projektu
- BE/KIV-IR - kořenový adresář pro backend projektu, zde je jádro vyhledávání (package core)
- FE/KIV-IR - kořenový adresář pro frontend projektu, aplikace je napsaná v Angularu a kominuje s backedndem pomocí JSON
- docker-compose.yml - soubor pro spuštění celé aplikace v dockeru (příkazem docker-compose pull a docker-compose up)
