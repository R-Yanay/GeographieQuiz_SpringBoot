package com.geographiequiz.service;

import com.geographiequiz.entity.Question;
import com.geographiequiz.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Initialisiert die H2-Datenbank mit Geographie-Quiz-Fragen beim Anwendungsstart.
 * Lädt Fragen zu Hauptstaedten, Flaggen, angrenzenden Laendern und Einwohnerzahlen
 * aus 5 Kontinenten mit verschiedenen Schwierigkeitsgraden.
 */
@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private QuestionRepository questionRepository;

    @Override
    public void run(String... args) {
        if (questionRepository.count() > 0) {
            System.out.println("Fragen sind bereits vorhanden. Initialisierung übersprungen.");
            return;
        }

        List<Question> questions = new ArrayList<>();

        // ============================================================
        // EUROPA — Hauptstaedte, Flaggen, Grenzen, Einwohner
        // ============================================================

        // Hauptstaedte
        questions.add(new Question("Was ist die Hauptstadt von Frankreich?", "Berlin", "London", "Paris", "Madrid", "C", "Europa", "easy"));
        questions.add(new Question("Was ist die Hauptstadt von Island?", "Oslo", "Reykjavik", "Helsinki", "Kopenhagen", "B", "Europa", "medium"));
        questions.add(new Question("Was ist die Hauptstadt von Portugal?", "Lissabon", "Porto", "Braga", "Coimbra", "A", "Europa", "easy"));
        questions.add(new Question("Was ist die Hauptstadt von Tschechien?", "Prag", "Bratislava", "Warschau", "Budapest", "A", "Europa", "medium"));
        questions.add(new Question("Was ist die Hauptstadt von Albanien?", "Tirana", "Skopje", "Podgorica", "Sarajevo", "A", "Europa", "hard"));
        questions.add(new Question("Was ist die Hauptstadt von Georgien?", "Tiflis", "Baku", "Eriwan", "Kiew", "A", "Europa", "hard"));

        // Flaggen
        questions.add(new Question("Welches Land hat ein Kreuz in Rot und Weiß auf seiner Flagge?", "Norwegen", "Dänemark", "Schweden", "Finnland", "A", "Europa", "medium"));
        questions.add(new Question("Welche Flagge zeigt einen roten Stern auf blauem Grund?", "Serbien", "Nordmazedonien", "Kroatien", "Slowenien", "B", "Europa", "hard"));
        questions.add(new Question("Welches Land hat eine Flagge mit drei horizontalen Streifen: Weiß, Blau, Rot?", "Russland", "Niederlande", "Finnland", "Estland", "A", "Europa", "medium"));
        questions.add(new Question("Welche Flagge zeigt einen Löwen mit Schwert?", "Zypern", "Malta", "Sizilien", "Kreta", "B", "Europa", "hard"));
        questions.add(new Question("Welches Land hat eine Flagge mit einem grünen Doppelkreuz auf Weiß?", "Ungarn", "Slowakei", "Österreich", "Polen", "A", "Europa", "medium"));
        questions.add(new Question("Welche Flagge zeigt einen roten Adler auf gelbem Grund?", "Slowakei", "Tschechien", "Polen", "Litauen", "A", "Europa", "hard"));

        // Angrenzende Laender
        questions.add(new Question("Welches Land grenzt an Deutschland, Frankreich und die Schweiz?", "Österreich", "Italien", "Belgien", "Luxemburg", "A", "Europa", "medium"));
        questions.add(new Question("Welches Land grenzt an die meisten anderen Laender in Europa?", "Deutschland", "Polen", "Ukraine", "Russland", "A", "Europa", "medium"));
        questions.add(new Question("Welches Land grenzt an Finnland, Norwegen und Dänemark?", "Schweden", "Estland", "Lettland", "Russland", "A", "Europa", "easy"));
        questions.add(new Question("Welches Land grenzt an Italien, Frankreich und die Schweiz?", "Liechtenstein", "Österreich", "San Marino", "Monaco", "A", "Europa", "hard"));
        questions.add(new Question("Welches Land grenzt an Polen, Tschechien und die Slowakei?", "Ukraine", "Deutschland", "Österreich", "Russland", "A", "Europa", "hard"));
        questions.add(new Question("Welches Land grenzt an Spanien und Portugal?", "Frankreich", "Marokko", "Andorra", "Gibraltar", "C", "Europa", "medium"));

        // Einwohnerzahlen
        questions.add(new Question("Welches europaeische Land hat die meisten Einwohner?", "Russland", "Deutschland", "Frankreich", "Vereinigtes Koenigreich", "A", "Europa", "easy"));
        questions.add(new Question("Welches Land hat etwa 83 Millionen Einwohner?", "Deutschland", "Frankreich", "Polen", "Spanien", "A", "Europa", "easy"));
        questions.add(new Question("Welches Land hat weniger als 1 Million Einwohner?", "Estland", "Slowakei", "Kroatien", "Rumänien", "A", "Europa", "medium"));
        questions.add(new Question("Welches Land hat etwa 38 Millionen Einwohner?", "Spanien", "Polen", "Ukraine", "Rumänien", "A", "Europa", "medium"));
        questions.add(new Question("Welches Land hat die wenigsten Einwohner in Europa?", "Vatikanstadt", "Monaco", "San Marino", "Liechtenstein", "A", "Europa", "hard"));
        questions.add(new Question("Welches Land hat etwa 10 Millionen Einwohner?", "Schweden", "Norwegen", "Finnland", "Dänemark", "C", "Europa", "hard"));

        // ============================================================
        // ASIEN — Hauptstaedte, Flaggen, Grenzen, Einwohner
        // ============================================================

        // Hauptstaedte
        questions.add(new Question("Was ist die Hauptstadt von Japan?", "Osaka", "Kyoto", "Tokio", "Hiroshima", "C", "Asien", "easy"));
        questions.add(new Question("Was ist die Hauptstadt von Südkorea?", "Busan", "Seoul", "Incheon", "Daegu", "B", "Asien", "medium"));
        questions.add(new Question("Was ist die Hauptstadt von Thailand?", "Bangkok", "Chiang Mai", "Phuket", "Pattaya", "A", "Asien", "easy"));
        questions.add(new Question("Was ist die Hauptstadt von Vietnam?", "Hanoi", "Ho Chi Minh Stadt", "Da Nang", "Hue", "A", "Asien", "medium"));
        questions.add(new Question("Was ist die Hauptstadt von Myanmar?", "Yangon", "Naypyidaw", "Mandalay", "Bago", "B", "Asien", "hard"));
        questions.add(new Question("Was ist die Hauptstadt von Kasachstan?", "Almaty", "Astana", "Schymkent", "Karaganda", "B", "Asien", "hard"));

        // Flaggen
        questions.add(new Question("Welches Land hat eine rote Sonne auf weißem Grund?", "Japan", "Südkorea", "Philippinen", "Indonesien", "A", "Asien", "easy"));
        questions.add(new Question("Welche Flagge zeigt einen Halbmond und einen Stern auf Rot?", "Türkei", "Pakistan", "Malaysia", "Indonesien", "A", "Asien", "medium"));
        questions.add(new Question("Welches Land hat eine Flagge mit Rot und Blau, geteilt durch Wellen?", "Südkorea", "Japan", "Mongolei", "Philippinen", "A", "Asien", "medium"));
        questions.add(new Question("Welche Flagge zeigt einen Tempel auf weißem Grund?", "Kambodscha", "Thailand", "Laos", "Myanmar", "A", "Asien", "hard"));
        questions.add(new Question("Welches Land hat eine Flagge mit einem grünen Halbmond und Stern auf Weiß?", "Pakistan", "Bangladesch", "Indonesien", "Malaysia", "A", "Asien", "medium"));
        questions.add(new Question("Welche Flagge zeigt einen roten Stern auf gelbem Grund?", "China", "Vietnam", "Laos", "Kambodscha", "A", "Asien", "easy"));

        // Angrenzende Laender
        questions.add(new Question("Welches Land grenzt an China, Indien und Nepal?", "Bhutan", "Myanmar", "Pakistan", "Bangladesch", "A", "Asien", "hard"));
        questions.add(new Question("Welches Land grenzt an die meisten anderen Laender in Asien?", "China", "Russland", "Indien", "Iran", "A", "Asien", "medium"));
        questions.add(new Question("Welches Land grenzt an Thailand, Laos und Kambodscha?", "Myanmar", "Vietnam", "Malaysia", "Indonesien", "A", "Asien", "hard"));
        questions.add(new Question("Welches Land grenzt an Indien, Nepal und Bangladesch?", "Myanmar", "China", "Pakistan", "Sri Lanka", "B", "Asien", "hard"));
        questions.add(new Question("Welches Land grenzt an die Türkei, Iran und Irak?", "Syrien", "Libanon", "Jordanien", "Israel", "A", "Asien", "hard"));
        questions.add(new Question("Welches Land grenzt an Indonesien und Malaysia?", "Singapur", "Philippinen", "Brunei", "Thailand", "C", "Asien", "medium"));

        // Einwohnerzahlen
        questions.add(new Question("Welches asiatische Land hat die meisten Einwohner?", "Indien", "China", "Indonesien", "Pakistan", "A", "Asien", "easy"));
        questions.add(new Question("Welches Land hat etwa 126 Millionen Einwohner?", "Indonesien", "Japan", "Philippinen", "Thailand", "A", "Asien", "medium"));
        questions.add(new Question("Welches Land hat etwa 125 Millionen Einwohner?", "Japan", "Philippinen", "Thailand", "Vietnam", "A", "Asien", "easy"));
        questions.add(new Question("Welches Land hat etwa 100 Millionen Einwohner?", "Philippinen", "Thailand", "Vietnam", "Myanmar", "A", "Asien", "medium"));
        questions.add(new Question("Welches Land hat weniger als 1 Million Einwohner?", "Malediven", "Singapur", "Timor-Leste", "Bhutan", "A", "Asien", "hard"));
        questions.add(new Question("Welches Land hat etwa 24 Millionen Einwohner?", "Thailand", "Vietnam", "Malaysia", "Kambodscha", "B", "Asien", "hard"));

        // ============================================================
        // AFRIKA — Hauptstaedte, Flaggen, Grenzen, Einwohner
        // ============================================================

        // Hauptstaedte
        questions.add(new Question("Was ist die Hauptstadt von Ägypten?", "Alexandria", "Kairo", "Luxor", "Aswan", "B", "Afrika", "easy"));
        questions.add(new Question("Was ist die Hauptstadt von Nigeria?", "Lagos", "Abuja", "Kano", "Ibadan", "B", "Afrika", "medium"));
        questions.add(new Question("Was ist die Hauptstadt von Kenia?", "Mombasa", "Nairobi", "Kisumu", "Nakuru", "B", "Afrika", "medium"));
        questions.add(new Question("Was ist die Hauptstadt von Marokko?", "Casablanca", "Rabat", "Marrakesch", "Fes", "B", "Afrika", "medium"));
        questions.add(new Question("Was ist die Hauptstadt von Tansania?", "Dar es Salaam", "Dodoma", "Arusha", "Mwanza", "B", "Afrika", "hard"));
        questions.add(new Question("Was ist die Hauptstadt von Simbabwe?", "Bulawayo", "Harare", "Mutare", "Gweru", "B", "Afrika", "hard"));

        // Flaggen
        questions.add(new Question("Welches Land hat eine Flagge mit Grün, Gelb und Rot sowie einem Stern?", "Ghana", "Senegal", "Kamerun", "Elfenbeinkueste", "A", "Afrika", "medium"));
        questions.add(new Question("Welche Flagge zeigt einen Löwen auf rotem Grund?", "Simbabwe", "Sambia", "Malawi", "Mosambik", "A", "Afrika", "hard"));
        questions.add(new Question("Welches Land hat eine Flagge mit Grün, Weiß und Rot (horizontal)?", "Italien", "Irland", "Nigeria", "Sudan", "C", "Afrika", "medium"));
        questions.add(new Question("Welche Flagge zeigt einen Halbmond und Stern auf Grün?", "Marokko", "Algerien", "Tunesien", "Libyen", "A", "Afrika", "medium"));
        questions.add(new Question("Welches Land hat eine Flagge mit Blau, Gelb und Grün?", "Äthiopien", "Eritrea", "Sudan", "Dschibuti", "A", "Afrika", "hard"));
        questions.add(new Question("Welche Flagge zeigt ein Wappen mit Elefanten?", "Simbabwe", "Sambia", "Malawi", "Mosambik", "A", "Afrika", "hard"));

        // Angrenzende Laender
        questions.add(new Question("Welches Land grenzt an Ägypten, Sudan und Libyen?", "Tschad", "Eritrea", "Algerien", "Tunesien", "A", "Afrika", "hard"));
        questions.add(new Question("Welches Land grenzt an Nigeria, Kamerun und Tschad?", "Niger", "Guinea", "Mali", "Senegal", "A", "Afrika", "hard"));
        questions.add(new Question("Welches Land grenzt an Kenia, Uganda und Tansania?", "Südsudan", "Somalia", "Ruanda", "Burundi", "A", "Afrika", "hard"));
        questions.add(new Question("Welches Land grenzt an Algerien, Marokko und Mauretanien?", "Tunesien", "Libyen", "Senegal", "Gambia", "A", "Afrika", "medium"));
        questions.add(new Question("Welches Land grenzt an Sambia, Simbabwe und Mosambik?", "Botswana", "Namibia", "Angola", "Tansania", "A", "Afrika", "hard"));
        questions.add(new Question("Welches Land grenzt an Äthiopien, Eritrea und Dschibuti?", "Somalia", "Sudan", "Kenia", "Südsudan", "A", "Afrika", "hard"));

        // Einwohnerzahlen
        questions.add(new Question("Welches afrikanische Land hat die meisten Einwohner?", "Nigeria", "Äthiopien", "Ägypten", "Kongo", "A", "Afrika", "easy"));
        questions.add(new Question("Welches Land hat etwa 220 Millionen Einwohner?", "Nigeria", "Äthiopien", "Indonesien", "Brasilien", "A", "Afrika", "easy"));
        questions.add(new Question("Welches Land hat etwa 100 Millionen Einwohner?", "Äthiopien", "Ägypten", "Südafrika", "Tansania", "A", "Afrika", "medium"));
        questions.add(new Question("Welches Land hat etwa 104 Millionen Einwohner?", "Ägypten", "Südafrika", "Tansania", "Kenia", "A", "Afrika", "medium"));
        questions.add(new Question("Welches Land hat weniger als 1 Million Einwohner?", "Seychellen", "Gabun", "Gambia", "Botswana", "A", "Afrika", "hard"));
        questions.add(new Question("Welches Land hat etwa 60 Millionen Einwohner?", "Südafrika", "Tansania", "Kenia", "Algerien", "A", "Afrika", "medium"));

        // ============================================================
        // AMERIKA — Hauptstaedte, Flaggen, Grenzen, Einwohner
        // ============================================================

        // Hauptstaedte
        questions.add(new Question("Was ist die Hauptstadt von Brasilien?", "São Paulo", "Rio de Janeiro", "Brasília", "Salvador", "C", "Amerika", "medium"));
        questions.add(new Question("Was ist die Hauptstadt von Kanada?", "Toronto", "Vancouver", "Ottawa", "Montreal", "C", "Amerika", "medium"));
        questions.add(new Question("Was ist die Hauptstadt von Argentinien?", "Rosario", "Buenos Aires", "Cordoba", "Mendoza", "B", "Amerika", "easy"));
        questions.add(new Question("Was ist die Hauptstadt von Kolumbien?", "Medellin", "Bogota", "Cali", "Cartagena", "B", "Amerika", "medium"));
        questions.add(new Question("Was ist die Hauptstadt von Chile?", "Valparaiso", "Santiago", "Concepcion", "Antofagasta", "B", "Amerika", "medium"));
        questions.add(new Question("Was ist die Hauptstadt von Guatemala?", "Guatemala-Stadt", "Antigua", "Quetzaltenango", "Tikal", "A", "Amerika", "hard"));

        // Flaggen
        questions.add(new Question("Welches Land hat eine Flagge mit Grün und Gelb sowie einem Stern?", "Brasilien", "Argentinien", "Kolumbien", "Venezuela", "A", "Amerika", "easy"));
        questions.add(new Question("Welche Flagge zeigt einen Kondor auf Blau und Weiß?", "Argentinien", "Uruguay", "Paraguay", "Chile", "A", "Amerika", "medium"));
        questions.add(new Question("Welches Land hat eine Flagge mit Rot, Weiß und Blau sowie einem roten Kreuz?", "Chile", "Kuba", "Haiti", "Dominikanische Republik", "A", "Amerika", "medium"));
        questions.add(new Question("Welche Flagge zeigt einen Adler, eine Schlange und einen Kaktus?", "Mexiko", "Guatemala", "Honduras", "El Salvador", "A", "Amerika", "easy"));
        questions.add(new Question("Welches Land hat eine Flagge mit Blau und Weiß (horizontal)?", "Honduras", "El Salvador", "Guatemala", "Nicaragua", "A", "Amerika", "hard"));
        questions.add(new Question("Welche Flagge zeigt einen roten Stern auf blauem Dreieck?", "Kuba", "Haiti", "Dominikanische Republik", "Puerto Rico", "A", "Amerika", "medium"));

        // Angrenzende Laender
        questions.add(new Question("Welches Land grenzt an die meisten anderen Laender in Sudamerika?", "Brasilien", "Argentinien", "Kolumbien", "Peru", "A", "Amerika", "medium"));
        questions.add(new Question("Welches Land grenzt an Mexiko und Guatemala?", "Belize", "Honduras", "El Salvador", "Kuba", "A", "Amerika", "medium"));
        questions.add(new Question("Welches Land grenzt an Argentinien, Chile und Bolivien?", "Peru", "Paraguay", "Brasilien", "Uruguay", "B", "Amerika", "hard"));
        questions.add(new Question("Welches Land grenzt an die USA und Mexiko?", "Kanada", "Kuba", "Haiti", "Belize", "A", "Amerika", "easy"));
        questions.add(new Question("Welches Land grenzt an Kolumbien, Venezuela und Brasilien?", "Guyana", "Peru", "Ecuador", "Suriname", "A", "Amerika", "hard"));
        questions.add(new Question("Welches Land grenzt an Peru, Brasilien und Argentinien?", "Bolivien", "Chile", "Paraguay", "Kolumbien", "A", "Amerika", "hard"));

        // Einwohnerzahlen
        questions.add(new Question("Welches amerikanische Land hat die meisten Einwohner?", "USA", "Brasilien", "Mexiko", "Argentinien", "A", "Amerika", "easy"));
        questions.add(new Question("Welches Land hat etwa 214 Millionen Einwohner?", "Brasilien", "Mexiko", "Indien", "Indonesien", "A", "Amerika", "easy"));
        questions.add(new Question("Welches Land hat etwa 128 Millionen Einwohner?", "Mexiko", "Brasilien", "Indien", "Indonesien", "A", "Amerika", "medium"));
        questions.add(new Question("Welches Land hat etwa 45 Millionen Einwohner?", "Kolumbien", "Argentinien", "Venezuela", "Peru", "A", "Amerika", "medium"));
        questions.add(new Question("Welches Land hat weniger als 1 Million Einwohner?", "Trinidad und Tobago", "Panama", "Uruguay", "Ecuador", "A", "Amerika", "hard"));
        questions.add(new Question("Welches Land hat etwa 19 Millionen Einwohner?", "Venezuela", "Peru", "Ecuador", "Kolumbien", "B", "Amerika", "hard"));

        // ============================================================
        // OZEANIEN — Hauptstaedte, Flaggen, Grenzen, Einwohner
        // ============================================================

        // Hauptstaedte
        questions.add(new Question("Was ist die Hauptstadt von Australien?", "Sydney", "Melbourne", "Canberra", "Brisbane", "C", "Ozeanien", "medium"));
        questions.add(new Question("Was ist die Hauptstadt von Neuseeland?", "Auckland", "Wellington", "Christchurch", "Dunedin", "B", "Ozeanien", "medium"));
        questions.add(new Question("Was ist die Hauptstadt von Fidschi?", "Suva", "Nadi", "Lautoka", "Labasa", "A", "Ozeanien", "hard"));
        questions.add(new Question("Was ist die Hauptstadt von Papua-Neuguinea?", "Port Moresby", "Lae", "Mount Hagen", "Madang", "A", "Ozeanien", "hard"));
        questions.add(new Question("Was ist die Hauptstadt von Samoa?", "Apia", "Tonga", "Nuku'alofa", "Honiara", "A", "Ozeanien", "hard"));
        questions.add(new Question("Was ist die Hauptstadt von Vanuatu?", "Port Vila", "Luganville", "Honiara", "Noumea", "A", "Ozeanien", "hard"));

        // Flaggen
        questions.add(new Question("Welches Land hat eine Flagge mit dem Union Jack und dem Southern Cross?", "Australien", "Neuseeland", "Fidschi", "Papua-Neuguinea", "A", "Ozeanien", "medium"));
        questions.add(new Question("Welche Flagge zeigt einen Kiwi-Vogel?", "Neuseeland", "Australien", "Fidschi", "Papua-Neuguinea", "A", "Ozeanien", "hard"));
        questions.add(new Question("Welches Land hat eine Flagge mit dem Union Jack und einem Adler?", "Fidschi", "Australien", "Neuseeland", "Papua-Neuguinea", "A", "Ozeanien", "hard"));
        questions.add(new Question("Welche Flagge zeigt einen Vogel auf rotem Grund?", "Papua-Neuguinea", "Fidschi", "Vanuatu", "Salomonen", "A", "Ozeanien", "hard"));
        questions.add(new Question("Welches Land hat eine Flagge mit dem Union Jack und drei Sternen?", "Neuseeland", "Australien", "Fidschi", "Tonga", "A", "Ozeanien", "medium"));
        questions.add(new Question("Welche Flagge zeigt eine Sonne auf blauem Grund?", "Salomonen", "Vanuatu", "Tonga", "Kiribati", "A", "Ozeanien", "hard"));

        // Angrenzende Laender (Inselstaaten — Ozeane und Nachbarn)
        questions.add(new Question("Welcher Ozean grenzt an Australien im Norden?", "Indischer Ozean", "Pazifischer Ozean", "Suedlicher Ozean", "Atlantischer Ozean", "B", "Ozeanien", "hard"));
        questions.add(new Question("Welches Land liegt am naechsten zu Neuseeland?", "Fidschi", "Australien", "Papua-Neuguinea", "Vanuatu", "B", "Ozeanien", "medium"));
        questions.add(new Question("Welches Land liegt am naechsten zu Australien?", "Papua-Neuguinea", "Indonesien", "Neuseeland", "Fidschi", "A", "Ozeanien", "medium"));
        questions.add(new Question("Welches Land liegt zwischen Australien und Neuseeland?", "Fidschi", "Vanuatu", "Neukaledonien", "Samoa", "A", "Ozeanien", "hard"));
        questions.add(new Question("Welches Land teilt sich die Insel Neuguinea?", "Papua-Neuguinea", "Indonesien", "Australien", "Fidschi", "A", "Ozeanien", "medium"));
        questions.add(new Question("Welches Land liegt naechst an Indonesien in Ozeanien?", "Papua-Neuguinea", "Philippinen", "Malaysia", "Timor-Leste", "A", "Ozeanien", "hard"));

        // Einwohnerzahlen
        questions.add(new Question("Welches Land in Ozeanien hat die meisten Einwohner?", "Australien", "Indonesien", "Papua-Neuguinea", "Neuseeland", "A", "Ozeanien", "easy"));
        questions.add(new Question("Welches Land hat etwa 26 Millionen Einwohner?", "Australien", "Indonesien", "Papua-Neuguinea", "Neuseeland", "A", "Ozeanien", "easy"));
        questions.add(new Question("Welches Land hat etwa 5 Millionen Einwohner?", "Neuseeland", "Fidschi", "Papua-Neuguinea", "Vanuatu", "A", "Ozeanien", "medium"));
        questions.add(new Question("Welches Land hat etwa 9 Millionen Einwohner?", "Papua-Neuguinea", "Fidschi", "Neuseeland", "Vanuatu", "A", "Ozeanien", "hard"));
        questions.add(new Question("Welches Land hat weniger als 1 Million Einwohner?", "Fidschi", "Papua-Neuguinea", "Neuseeland", "Vanuatu", "A", "Ozeanien", "medium"));
        questions.add(new Question("Welches Land hat etwa 300.000 Einwohner?", "Vanuatu", "Fidschi", "Samoa", "Tonga", "A", "Ozeanien", "hard"));

        // ============================================================
        // ZUSATZ: Weltweite Fragen (Flaggen, Grenzen, Einwohner)
        // ============================================================

        // Weltweite Flaggen
        questions.add(new Question("Welches Land hat eine komplett gelbe Flagge mit einem Wappen?", "Vatikanstadt", "San Marino", "Monaco", "Liechtenstein", "A", "Europa", "hard"));
        questions.add(new Question("Welches Land hat eine Flagge mit nur einer Farbe (Grün)?", "Libyen", "Marokko", "Algerien", "Tunesien", "A", "Afrika", "hard"));
        questions.add(new Question("Welches Land hat eine Flagge mit einem Regenbogen?", "Mosambik", "Sambia", "Simbabwe", "Malawi", "A", "Afrika", "hard"));
        questions.add(new Question("Welches Land hat eine Flagge mit einem Pfeil und Bogen?", "Malediven", "Sri Lanka", "Bangladesch", "Nepal", "A", "Asien", "hard"));
        questions.add(new Question("Welches Land hat eine Flagge mit einem Quetzal-Vogel?", "Guatemala", "Honduras", "El Salvador", "Mexiko", "A", "Amerika", "hard"));
        questions.add(new Question("Welches Land hat eine Flagge mit einem Kranich und einer Orchidee?", "Südkorea", "Japan", "China", "Mongolei", "A", "Asien", "hard"));

        // Weltweite Grenzen
        questions.add(new Question("Welches Land grenzt an die meisten anderen Laender der Welt?", "China", "Russland", "Brasilien", "Deutschland", "A", "Asien", "medium"));
        questions.add(new Question("Welches Land grenzt an die USA und Kanada?", "Mexiko", "Grönland", "Kuba", "Russland", "A", "Amerika", "easy"));
        questions.add(new Question("Welches Land grenzt an Russland, China und Nordkorea?", "Südkorea", "Japan", "Mongolei", "Indien", "C", "Asien", "medium"));
        questions.add(new Question("Welches Land grenzt an Brasilien, Peru und Ecuador?", "Kolumbien", "Venezuela", "Bolivien", "Chile", "A", "Amerika", "medium"));
        questions.add(new Question("Welches Land grenzt an die USA?", "Mexiko", "Kanada", "Russland", "Kuba", "A", "Amerika", "easy"));
        questions.add(new Question("Welches Land grenzt an Russland und die USA (im Meer)?", "Kanada", "Grönland", "Island", "Norwegen", "A", "Amerika", "hard"));

        // Weltweite Einwohner
        questions.add(new Question("Welches Land hat die meisten Einwohner der Welt?", "Indien", "China", "USA", "Indonesien", "A", "Asien", "easy"));
        questions.add(new Question("Welches Land hat etwa 1,4 Milliarden Einwohner?", "China", "Indien", "USA", "Indonesien", "A", "Asien", "easy"));
        questions.add(new Question("Welches Land hat etwa 331 Millionen Einwohner?", "USA", "China", "Indien", "Indonesien", "A", "Amerika", "easy"));
        questions.add(new Question("Welches Land hat etwa 141 Millionen Einwohner?", "Indonesien", "Pakistan", "Bangladesch", "Nigeria", "A", "Asien", "medium"));
        questions.add(new Question("Welches Land hat etwa 218 Millionen Einwohner?", "Indien", "China", "USA", "Indonesien", "A", "Asien", "hard"));
        questions.add(new Question("Welches Land hat die wenigsten Einwohner der Welt?", "Vatikanstadt", "Monaco", "Nauru", "Tuvalu", "A", "Europa", "hard"));

        questionRepository.saveAll(questions);
        System.out.println(questions.size() + " Geographie-Fragen wurden in die Datenbank geladen.");
    }
}
