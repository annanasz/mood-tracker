# Házi feladat specifikáció

## Mobil- és webes szoftverek
### 2022.11.20.
### MOOd
### Vass Annamária - (IR4JAJ)
### vassannamaria01@gmail.com 
### Laborvezető: Kapitány Erik

## Bemutatás

Egy hangulat napló (mood tracker) alkalmazást fogok készíteni, amelyben lehetőség van minden nap egy bejegyzést írni az aznapi hangulatról, élményekről, tevékenységekről, stb. Ezen felül különböző célokat is nyilván lehet tartani, és nyomonkövetni, hogy melyik napokon mennyire járult hozzá a felhasználó az adott célhoz. <br />
Az app azzal a céllal jött létre, hogy könnyedén lehessen reflektálni a napi történésekről, pozitívumokról, negatívumokról és egyben motiváció arra is, hogy megvalósítsuk a céljaink és önmagunkat. Az alkalmazás célközönsége főként a fiatalabb korosztály, akik nap végén szeretnék összeszedni a gondolataikat, így az később is visszanézhető, feleleveníthető.

## Főbb funkciók

1. Az alkalmazásban lehetőség van új napi bejegyzés felvételére, ahol a felhasználónak különböző adatokat kell megadnia a napjáról pl. textboxok és gombok segítségével. Az adatok adatbázisban kerülnek tárolásra. 
2. A napi bejegyzések egy listában jelennek majd meg, rájuk kattintva részletesebb nézetben megtekinthetőek az aznapi bevitt adatok.
3. A bejegyzéseket törölni is lehet az adatbázisból, illetve utólagosan szerkeszteni.
4. A célok fül alatt új célok adhatóak hozzá, amik ugyancsak elmentésre kerülnek adatbázis segítségével, a napi bejegyzéseknél megjelennek a célok is, hogy az adott napokon mennyire járultunk hozzá az adott célhoz.
5. A naptár fül alatt megtekinthető naptár nézetben, hogy melyik napon milyen volt a felhasználó hangulata az eddig bevitt adatok alapján.

### További opcionális funkciók (ráéréstől függ):
1. Fénykép hozzáadása napi bejegyzésekhez
2. Statisztikák fül amely különböző módon elemzi és grafikusan megjeleníti az eddigi adatokat, pl. boldog/szomorú napok aránya.
3. Minden nap megadott órában értesítés a bejegyzés készítéséről
4. Animációk

## Választott technológiák:

- UI - Activity-k, Fragmentek, Splash képernyő
- Stílusok, témák használata
- RecyclerView - a listák megjelenítéséhez
- Perzisztens adattárolás - adatbázis segítségével


# Házi feladat dokumentáció

## Felhasználói dokumentáció:
Az alkalmazás naplószerű napi bejegyzések bevitelére ad lehetőséget, amellyel nyomon lehet követni a mindennapos hangulatunkat, valamint a kitűzött céljainkat is megadhatjuk, és hogy melyik nap mennyire járultunk hozzá a teljesülésükhöz.


Az alkalmazást megnyitva a Splash képernyő után rögtön a napi bejegyzések listája fogadja a felhasználót. Alul a 4 gombbal lehet navigálni az alkalmazás 4 főképernyője közt, vagy akár lapozással is lehet váltani. A 4 képernyő:

 #### **1. Daily Entries: A napi bejegyzések**<br>
&nbsp;&nbsp;&nbsp;&nbsp;Itt lehet megtekinteni az eddig bevitt napi bejegyzéseket, látható a dátum és mellette a hozzá tartozó kép, amit az aznapi megadott hangulat határoz meg.

&nbsp;&nbsp;&nbsp;&nbsp;A jobb alsó sarokban található gombbal lehet felvenni új bejegyzést, ezt megnyomva egy új ablak nyílik meg, ahol meg lehet adni a napi bejegyzés adatait. Itt be tudjuk jelölni azt is, hogy a hozzáadott célok közül melyikekhez jároultunk hozzá aznap. Ha meggondoltuk magunkat, a *cancel* gombbal visszaléphetünk az előző oldalra, ha pedig készen vagyunk az adatok megadásával, a *save* gombbal elmenthetjük azokat.

&nbsp;&nbsp;&nbsp;&nbsp;Ha rákattintunk egy elemre, akkor megjelenik annak a bejegyzésnek részletes nézete minden megadott adattal együtt egy új ablakban. A *cancel* gombbal kiléphetünk ebből a nézetből. Ha a jobb sarokban levő *options* gombra kattintunk, megjelenik két opció, a *kuka ikonnal* törölhetjük a bejegyzést, a *ceruza ikonra* kattintva pedig megnyílik a bejegyzés szerkesztő ablak, ahol szerkeszthetjük az adott bejegyzés adatait (ennek működése hasonló az új bejegyzés hozzáadásához)

#### **2. Calendar: Naptár nézet**<br>
&nbsp;&nbsp;&nbsp;&nbsp;Erre az oldalra navigálva egy naptárat láthatunk, amelyben kiválasztva egy napot, a naptár alatt megjelennek az aznapi hangulatok. Ha nem volt a kiválasztott napon egyetlen bejegyzés sem, a lista üres.

#### **3. Charts: Statisztika**<br>
&nbsp;&nbsp;&nbsp;&nbsp;Ezen az oldalon az eddig bevitt adatok alapán egy kördiagram látható a napi hangulatok arányának alapján. 

#### **4. Goals: Célok**<br>
&nbsp;&nbsp;&nbsp;&nbsp;Itt található az eddig felvett célok listája. A célok mellett megjelenő kép a kategóriától függ. Ha egy célra rákattintunk, megjelnik a hozzátartozó leírás, valamint egy *kuka ikon*, amivel törölhetjük a célt. Ha törlünk egy célt, a napi bejegyzések közül is törlődik.

&nbsp;&nbsp;&nbsp;&nbsp;A jobb sarokban található *plusz gombbal* új célt adhatunk hozzá, amelynek meg kell adnunk az adatait, valamint kiválasztani, hogy milyen kategóriába tartozik. Ha új célt adunk hozzá, az megjelenik a napi bejegyzéseknél.

## Program struktúrájának áttekintése:

&nbsp;&nbsp;&nbsp;&nbsp;A *MainActivityben* található egy *ViewPager*, ez teszi lehetővé a 4 fő *fragment* közti váltogatást lapozással, valamint ez össze van kötve a  *BottomNavigationBarral*, amelyen a gombok segítségével is válthatunk a nézetek közt. A célokat, napi bejegyzéseket, tehát az alkalmazáshoz tartozó adatokat perzisztensen tárolom a *Room ORM* könyvtár segítségével. A két entitás tárolásán kívül még a célok és napi bejegyzések közti kapcsolatának eltárolására is van egy tábla, így tárolom el. hogy melyik bejegyzéshez melyik célok tartoznak. Az adatbázis minden új adat hozzáadása esetén frissül, és pl. a kördiagram is változik ha hozzáadunk egy új bejegyzést, tehát mindegyik képernyő friss adatokat lát. 

&nbsp;&nbsp;&nbsp;&nbsp;A napi bejegyzés hozzáadása egy új *activityben* valósul meg, valamint egy bejegyzés részletes nézetének megtekintéséhez is létrejön egy *activity*. Ezeket *Intentek* segítségével indítom el, amelyeken keresztül adatot is adok nekik át. *Partialized data*-ként adom át az általam létrehozott attribútumokat, mint pl a cél(Goal) vagy DailyEntry(napi bejegyzés).

&nbsp;&nbsp;&nbsp;&nbsp;Az új cél hozzáadásához *AlertDialogot* és *DialogFragmentet* használok.

&nbsp;&nbsp;&nbsp;&nbsp;A kördiagram a statisztika képernyőn az *MPAndroidChart* külső könyvtár segítségével lett létrehozva.

## Extra funkconalitás bemutatása:
&nbsp;&nbsp;&nbsp;&nbsp;Az egyik extra funkcionalitás a Chart fülön található PieChart, amely az eddig bevitt adatok alapján képes megjeleníteni grafikusan, hogy az eddigi bejegyzések alapján milyen a hangulatok aránya.

&nbsp;&nbsp;&nbsp;&nbsp;A napi bejegyzések részletes képernyőjén a szerkesztés és törlés gombok animációval jelennek meg, ezzel is hozzájárulva a felhasználói felület igényesebb megjelenéséhez.

&nbsp;&nbsp;&nbsp;&nbsp;A napi hangulat kiválasztásánál normál *RadioButtonok* helyett képek jelennek meg, általuk kiválasztható a hangulat.

&nbsp;&nbsp;&nbsp;&nbsp;Az alkalmazás felhasználói felületét *Toolbar* színesíti, valamint különböző képek, a *splash képernyő* és a betűtítpus is hozzáad a felhasználói élményhez.

## Felhasznált technológiák:
- Activity
- Fragment
- ViewPager
- BottomNavBar
- ToolBar
- Intent
- Room ORM
- RecyclerView
- Animáció
- Stílusok, témák