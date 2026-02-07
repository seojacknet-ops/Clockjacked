package com.clockjacked.app.data

/**
 * Static database of city-to-timezone mappings used for search and selection.
 * Contains 300+ entries covering world capitals, major cities, travel destinations,
 * and cities in uncommon offset zones (half-hour, quarter-hour).
 *
 * All timezone IDs are valid IANA identifiers compatible with java.time.ZoneId.
 */

data class CityTimezone(
    val city: String,
    val country: String,
    val timezoneId: String,
    val flagEmoji: String
)

object TimezoneDatabase {

    val cities: List<CityTimezone> = listOf(
        // ── Default clocks ──────────────────────────────────────────────
        CityTimezone("Salt Lake City", "United States", "America/Denver", "\uD83C\uDDFA\uD83C\uDDF8"),
        CityTimezone("Honolulu", "United States", "Pacific/Honolulu", "\uD83C\uDDFA\uD83C\uDDF8"),
        CityTimezone("Liverpool", "United Kingdom", "Europe/London", "\uD83C\uDDEC\uD83C\uDDE7"),
        CityTimezone("Bali", "Indonesia", "Asia/Makassar", "\uD83C\uDDEE\uD83C\uDDE9"),

        // ── North America ───────────────────────────────────────────────
        CityTimezone("New York", "United States", "America/New_York", "\uD83C\uDDFA\uD83C\uDDF8"),
        CityTimezone("Los Angeles", "United States", "America/Los_Angeles", "\uD83C\uDDFA\uD83C\uDDF8"),
        CityTimezone("Chicago", "United States", "America/Chicago", "\uD83C\uDDFA\uD83C\uDDF8"),
        CityTimezone("Houston", "United States", "America/Chicago", "\uD83C\uDDFA\uD83C\uDDF8"),
        CityTimezone("Phoenix", "United States", "America/Phoenix", "\uD83C\uDDFA\uD83C\uDDF8"),
        CityTimezone("Philadelphia", "United States", "America/New_York", "\uD83C\uDDFA\uD83C\uDDF8"),
        CityTimezone("San Antonio", "United States", "America/Chicago", "\uD83C\uDDFA\uD83C\uDDF8"),
        CityTimezone("San Diego", "United States", "America/Los_Angeles", "\uD83C\uDDFA\uD83C\uDDF8"),
        CityTimezone("Dallas", "United States", "America/Chicago", "\uD83C\uDDFA\uD83C\uDDF8"),
        CityTimezone("San Francisco", "United States", "America/Los_Angeles", "\uD83C\uDDFA\uD83C\uDDF8"),
        CityTimezone("Seattle", "United States", "America/Los_Angeles", "\uD83C\uDDFA\uD83C\uDDF8"),
        CityTimezone("Denver", "United States", "America/Denver", "\uD83C\uDDFA\uD83C\uDDF8"),
        CityTimezone("Boston", "United States", "America/New_York", "\uD83C\uDDFA\uD83C\uDDF8"),
        CityTimezone("Nashville", "United States", "America/Chicago", "\uD83C\uDDFA\uD83C\uDDF8"),
        CityTimezone("Las Vegas", "United States", "America/Los_Angeles", "\uD83C\uDDFA\uD83C\uDDF8"),
        CityTimezone("Detroit", "United States", "America/Detroit", "\uD83C\uDDFA\uD83C\uDDF8"),
        CityTimezone("Miami", "United States", "America/New_York", "\uD83C\uDDFA\uD83C\uDDF8"),
        CityTimezone("Atlanta", "United States", "America/New_York", "\uD83C\uDDFA\uD83C\uDDF8"),
        CityTimezone("Minneapolis", "United States", "America/Chicago", "\uD83C\uDDFA\uD83C\uDDF8"),
        CityTimezone("Portland", "United States", "America/Los_Angeles", "\uD83C\uDDFA\uD83C\uDDF8"),
        CityTimezone("Anchorage", "United States", "America/Anchorage", "\uD83C\uDDFA\uD83C\uDDF8"),
        CityTimezone("Washington D.C.", "United States", "America/New_York", "\uD83C\uDDFA\uD83C\uDDF8"),
        CityTimezone("Ottawa", "Canada", "America/Toronto", "\uD83C\uDDE8\uD83C\uDDE6"),
        CityTimezone("Toronto", "Canada", "America/Toronto", "\uD83C\uDDE8\uD83C\uDDE6"),
        CityTimezone("Vancouver", "Canada", "America/Vancouver", "\uD83C\uDDE8\uD83C\uDDE6"),
        CityTimezone("Montreal", "Canada", "America/Toronto", "\uD83C\uDDE8\uD83C\uDDE6"),
        CityTimezone("Calgary", "Canada", "America/Edmonton", "\uD83C\uDDE8\uD83C\uDDE6"),
        CityTimezone("Edmonton", "Canada", "America/Edmonton", "\uD83C\uDDE8\uD83C\uDDE6"),
        CityTimezone("Winnipeg", "Canada", "America/Winnipeg", "\uD83C\uDDE8\uD83C\uDDE6"),
        CityTimezone("Halifax", "Canada", "America/Halifax", "\uD83C\uDDE8\uD83C\uDDE6"),
        CityTimezone("St. John's", "Canada", "America/St_Johns", "\uD83C\uDDE8\uD83C\uDDE6"),
        CityTimezone("Mexico City", "Mexico", "America/Mexico_City", "\uD83C\uDDF2\uD83C\uDDFD"),
        CityTimezone("Cancun", "Mexico", "America/Cancun", "\uD83C\uDDF2\uD83C\uDDFD"),
        CityTimezone("Guadalajara", "Mexico", "America/Mexico_City", "\uD83C\uDDF2\uD83C\uDDFD"),
        CityTimezone("Tijuana", "Mexico", "America/Tijuana", "\uD83C\uDDF2\uD83C\uDDFD"),

        // ── Central America & Caribbean ─────────────────────────────────
        CityTimezone("Guatemala City", "Guatemala", "America/Guatemala", "\uD83C\uDDEC\uD83C\uDDF9"),
        CityTimezone("San Salvador", "El Salvador", "America/El_Salvador", "\uD83C\uDDF8\uD83C\uDDFB"),
        CityTimezone("Tegucigalpa", "Honduras", "America/Tegucigalpa", "\uD83C\uDDED\uD83C\uDDF3"),
        CityTimezone("Managua", "Nicaragua", "America/Managua", "\uD83C\uDDF3\uD83C\uDDEE"),
        CityTimezone("San Jose", "Costa Rica", "America/Costa_Rica", "\uD83C\uDDE8\uD83C\uDDF7"),
        CityTimezone("Panama City", "Panama", "America/Panama", "\uD83C\uDDF5\uD83C\uDDE6"),
        CityTimezone("Havana", "Cuba", "America/Havana", "\uD83C\uDDE8\uD83C\uDDFA"),
        CityTimezone("Kingston", "Jamaica", "America/Jamaica", "\uD83C\uDDEF\uD83C\uDDF2"),
        CityTimezone("Nassau", "Bahamas", "America/Nassau", "\uD83C\uDDE7\uD83C\uDDF8"),
        CityTimezone("Port-au-Prince", "Haiti", "America/Port-au-Prince", "\uD83C\uDDED\uD83C\uDDF9"),
        CityTimezone("Santo Domingo", "Dominican Republic", "America/Santo_Domingo", "\uD83C\uDDE9\uD83C\uDDF4"),
        CityTimezone("San Juan", "Puerto Rico", "America/Puerto_Rico", "\uD83C\uDDF5\uD83C\uDDF7"),
        CityTimezone("Belmopan", "Belize", "America/Belize", "\uD83C\uDDE7\uD83C\uDDFF"),
        CityTimezone("Bridgetown", "Barbados", "America/Barbados", "\uD83C\uDDE7\uD83C\uDDE7"),
        CityTimezone("Port of Spain", "Trinidad and Tobago", "America/Port_of_Spain", "\uD83C\uDDF9\uD83C\uDDF9"),

        // ── South America ───────────────────────────────────────────────
        CityTimezone("Bogota", "Colombia", "America/Bogota", "\uD83C\uDDE8\uD83C\uDDF4"),
        CityTimezone("Lima", "Peru", "America/Lima", "\uD83C\uDDF5\uD83C\uDDEA"),
        CityTimezone("Santiago", "Chile", "America/Santiago", "\uD83C\uDDE8\uD83C\uDDF1"),
        CityTimezone("Buenos Aires", "Argentina", "America/Argentina/Buenos_Aires", "\uD83C\uDDE6\uD83C\uDDF7"),
        CityTimezone("Brasilia", "Brazil", "America/Sao_Paulo", "\uD83C\uDDE7\uD83C\uDDF7"),
        CityTimezone("Sao Paulo", "Brazil", "America/Sao_Paulo", "\uD83C\uDDE7\uD83C\uDDF7"),
        CityTimezone("Rio de Janeiro", "Brazil", "America/Sao_Paulo", "\uD83C\uDDE7\uD83C\uDDF7"),
        CityTimezone("Manaus", "Brazil", "America/Manaus", "\uD83C\uDDE7\uD83C\uDDF7"),
        CityTimezone("Quito", "Ecuador", "America/Guayaquil", "\uD83C\uDDEA\uD83C\uDDE8"),
        CityTimezone("Guayaquil", "Ecuador", "America/Guayaquil", "\uD83C\uDDEA\uD83C\uDDE8"),
        CityTimezone("Caracas", "Venezuela", "America/Caracas", "\uD83C\uDDFB\uD83C\uDDEA"),
        CityTimezone("Montevideo", "Uruguay", "America/Montevideo", "\uD83C\uDDFA\uD83C\uDDFE"),
        CityTimezone("Asuncion", "Paraguay", "America/Asuncion", "\uD83C\uDDF5\uD83C\uDDFE"),
        CityTimezone("La Paz", "Bolivia", "America/La_Paz", "\uD83C\uDDE7\uD83C\uDDF4"),
        CityTimezone("Georgetown", "Guyana", "America/Guyana", "\uD83C\uDDEC\uD83C\uDDFE"),
        CityTimezone("Paramaribo", "Suriname", "America/Paramaribo", "\uD83C\uDDF8\uD83C\uDDF7"),
        CityTimezone("Cayenne", "French Guiana", "America/Cayenne", "\uD83C\uDDEC\uD83C\uDDEB"),
        CityTimezone("Medellin", "Colombia", "America/Bogota", "\uD83C\uDDE8\uD83C\uDDF4"),
        CityTimezone("Cartagena", "Colombia", "America/Bogota", "\uD83C\uDDE8\uD83C\uDDF4"),

        // ── Western Europe ──────────────────────────────────────────────
        CityTimezone("London", "United Kingdom", "Europe/London", "\uD83C\uDDEC\uD83C\uDDE7"),
        CityTimezone("Edinburgh", "United Kingdom", "Europe/London", "\uD83C\uDDEC\uD83C\uDDE7"),
        CityTimezone("Manchester", "United Kingdom", "Europe/London", "\uD83C\uDDEC\uD83C\uDDE7"),
        CityTimezone("Birmingham", "United Kingdom", "Europe/London", "\uD83C\uDDEC\uD83C\uDDE7"),
        CityTimezone("Dublin", "Ireland", "Europe/Dublin", "\uD83C\uDDEE\uD83C\uDDEA"),
        CityTimezone("Paris", "France", "Europe/Paris", "\uD83C\uDDEB\uD83C\uDDF7"),
        CityTimezone("Lyon", "France", "Europe/Paris", "\uD83C\uDDEB\uD83C\uDDF7"),
        CityTimezone("Marseille", "France", "Europe/Paris", "\uD83C\uDDEB\uD83C\uDDF7"),
        CityTimezone("Nice", "France", "Europe/Paris", "\uD83C\uDDEB\uD83C\uDDF7"),
        CityTimezone("Berlin", "Germany", "Europe/Berlin", "\uD83C\uDDE9\uD83C\uDDEA"),
        CityTimezone("Munich", "Germany", "Europe/Berlin", "\uD83C\uDDE9\uD83C\uDDEA"),
        CityTimezone("Frankfurt", "Germany", "Europe/Berlin", "\uD83C\uDDE9\uD83C\uDDEA"),
        CityTimezone("Hamburg", "Germany", "Europe/Berlin", "\uD83C\uDDE9\uD83C\uDDEA"),
        CityTimezone("Madrid", "Spain", "Europe/Madrid", "\uD83C\uDDEA\uD83C\uDDF8"),
        CityTimezone("Barcelona", "Spain", "Europe/Madrid", "\uD83C\uDDEA\uD83C\uDDF8"),
        CityTimezone("Rome", "Italy", "Europe/Rome", "\uD83C\uDDEE\uD83C\uDDF9"),
        CityTimezone("Milan", "Italy", "Europe/Rome", "\uD83C\uDDEE\uD83C\uDDF9"),
        CityTimezone("Venice", "Italy", "Europe/Rome", "\uD83C\uDDEE\uD83C\uDDF9"),
        CityTimezone("Florence", "Italy", "Europe/Rome", "\uD83C\uDDEE\uD83C\uDDF9"),
        CityTimezone("Naples", "Italy", "Europe/Rome", "\uD83C\uDDEE\uD83C\uDDF9"),
        CityTimezone("Lisbon", "Portugal", "Europe/Lisbon", "\uD83C\uDDF5\uD83C\uDDF9"),
        CityTimezone("Porto", "Portugal", "Europe/Lisbon", "\uD83C\uDDF5\uD83C\uDDF9"),
        CityTimezone("Amsterdam", "Netherlands", "Europe/Amsterdam", "\uD83C\uDDF3\uD83C\uDDF1"),
        CityTimezone("Brussels", "Belgium", "Europe/Brussels", "\uD83C\uDDE7\uD83C\uDDEA"),
        CityTimezone("Luxembourg", "Luxembourg", "Europe/Luxembourg", "\uD83C\uDDF1\uD83C\uDDFA"),
        CityTimezone("Zurich", "Switzerland", "Europe/Zurich", "\uD83C\uDDE8\uD83C\uDDED"),
        CityTimezone("Geneva", "Switzerland", "Europe/Zurich", "\uD83C\uDDE8\uD83C\uDDED"),
        CityTimezone("Bern", "Switzerland", "Europe/Zurich", "\uD83C\uDDE8\uD83C\uDDED"),
        CityTimezone("Vienna", "Austria", "Europe/Vienna", "\uD83C\uDDE6\uD83C\uDDF9"),
        CityTimezone("Monaco", "Monaco", "Europe/Monaco", "\uD83C\uDDF2\uD83C\uDDE8"),
        CityTimezone("Andorra la Vella", "Andorra", "Europe/Andorra", "\uD83C\uDDE6\uD83C\uDDE9"),
        CityTimezone("Vaduz", "Liechtenstein", "Europe/Vaduz", "\uD83C\uDDF1\uD83C\uDDEE"),
        CityTimezone("San Marino", "San Marino", "Europe/San_Marino", "\uD83C\uDDF8\uD83C\uDDF2"),
        CityTimezone("Reykjavik", "Iceland", "Atlantic/Reykjavik", "\uD83C\uDDEE\uD83C\uDDF8"),

        // ── Northern Europe & Scandinavia ───────────────────────────────
        CityTimezone("Copenhagen", "Denmark", "Europe/Copenhagen", "\uD83C\uDDE9\uD83C\uDDF0"),
        CityTimezone("Stockholm", "Sweden", "Europe/Stockholm", "\uD83C\uDDF8\uD83C\uDDEA"),
        CityTimezone("Oslo", "Norway", "Europe/Oslo", "\uD83C\uDDF3\uD83C\uDDF4"),
        CityTimezone("Helsinki", "Finland", "Europe/Helsinki", "\uD83C\uDDEB\uD83C\uDDEE"),
        CityTimezone("Tallinn", "Estonia", "Europe/Tallinn", "\uD83C\uDDEA\uD83C\uDDEA"),
        CityTimezone("Riga", "Latvia", "Europe/Riga", "\uD83C\uDDF1\uD83C\uDDFB"),
        CityTimezone("Vilnius", "Lithuania", "Europe/Vilnius", "\uD83C\uDDF1\uD83C\uDDF9"),

        // ── Central & Eastern Europe ────────────────────────────────────
        CityTimezone("Warsaw", "Poland", "Europe/Warsaw", "\uD83C\uDDF5\uD83C\uDDF1"),
        CityTimezone("Krakow", "Poland", "Europe/Warsaw", "\uD83C\uDDF5\uD83C\uDDF1"),
        CityTimezone("Prague", "Czech Republic", "Europe/Prague", "\uD83C\uDDE8\uD83C\uDDFF"),
        CityTimezone("Bratislava", "Slovakia", "Europe/Bratislava", "\uD83C\uDDF8\uD83C\uDDF0"),
        CityTimezone("Budapest", "Hungary", "Europe/Budapest", "\uD83C\uDDED\uD83C\uDDFA"),
        CityTimezone("Bucharest", "Romania", "Europe/Bucharest", "\uD83C\uDDF7\uD83C\uDDF4"),
        CityTimezone("Sofia", "Bulgaria", "Europe/Sofia", "\uD83C\uDDE7\uD83C\uDDEC"),
        CityTimezone("Belgrade", "Serbia", "Europe/Belgrade", "\uD83C\uDDF7\uD83C\uDDF8"),
        CityTimezone("Zagreb", "Croatia", "Europe/Zagreb", "\uD83C\uDDED\uD83C\uDDF7"),
        CityTimezone("Ljubljana", "Slovenia", "Europe/Ljubljana", "\uD83C\uDDF8\uD83C\uDDEE"),
        CityTimezone("Sarajevo", "Bosnia and Herzegovina", "Europe/Sarajevo", "\uD83C\uDDE7\uD83C\uDDE6"),
        CityTimezone("Podgorica", "Montenegro", "Europe/Podgorica", "\uD83C\uDDF2\uD83C\uDDEA"),
        CityTimezone("Skopje", "North Macedonia", "Europe/Skopje", "\uD83C\uDDF2\uD83C\uDDF0"),
        CityTimezone("Tirana", "Albania", "Europe/Tirane", "\uD83C\uDDE6\uD83C\uDDF1"),
        CityTimezone("Pristina", "Kosovo", "Europe/Belgrade", "\uD83C\uDDFD\uD83C\uDDF0"),
        CityTimezone("Chisinau", "Moldova", "Europe/Chisinau", "\uD83C\uDDF2\uD83C\uDDE9"),

        // ── Eastern Europe & Russia ─────────────────────────────────────
        CityTimezone("Moscow", "Russia", "Europe/Moscow", "\uD83C\uDDF7\uD83C\uDDFA"),
        CityTimezone("Saint Petersburg", "Russia", "Europe/Moscow", "\uD83C\uDDF7\uD83C\uDDFA"),
        CityTimezone("Novosibirsk", "Russia", "Asia/Novosibirsk", "\uD83C\uDDF7\uD83C\uDDFA"),
        CityTimezone("Yekaterinburg", "Russia", "Asia/Yekaterinburg", "\uD83C\uDDF7\uD83C\uDDFA"),
        CityTimezone("Vladivostok", "Russia", "Asia/Vladivostok", "\uD83C\uDDF7\uD83C\uDDFA"),
        CityTimezone("Kaliningrad", "Russia", "Europe/Kaliningrad", "\uD83C\uDDF7\uD83C\uDDFA"),
        CityTimezone("Krasnoyarsk", "Russia", "Asia/Krasnoyarsk", "\uD83C\uDDF7\uD83C\uDDFA"),
        CityTimezone("Irkutsk", "Russia", "Asia/Irkutsk", "\uD83C\uDDF7\uD83C\uDDFA"),
        CityTimezone("Yakutsk", "Russia", "Asia/Yakutsk", "\uD83C\uDDF7\uD83C\uDDFA"),
        CityTimezone("Magadan", "Russia", "Asia/Magadan", "\uD83C\uDDF7\uD83C\uDDFA"),
        CityTimezone("Kamchatka", "Russia", "Asia/Kamchatka", "\uD83C\uDDF7\uD83C\uDDFA"),
        CityTimezone("Kyiv", "Ukraine", "Europe/Kyiv", "\uD83C\uDDFA\uD83C\uDDE6"),
        CityTimezone("Minsk", "Belarus", "Europe/Minsk", "\uD83C\uDDE7\uD83C\uDDFE"),

        // ── Southeastern Europe & Mediterranean ─────────────────────────
        CityTimezone("Athens", "Greece", "Europe/Athens", "\uD83C\uDDEC\uD83C\uDDF7"),
        CityTimezone("Istanbul", "Turkey", "Europe/Istanbul", "\uD83C\uDDF9\uD83C\uDDF7"),
        CityTimezone("Ankara", "Turkey", "Europe/Istanbul", "\uD83C\uDDF9\uD83C\uDDF7"),
        CityTimezone("Nicosia", "Cyprus", "Asia/Nicosia", "\uD83C\uDDE8\uD83C\uDDFE"),
        CityTimezone("Valletta", "Malta", "Europe/Malta", "\uD83C\uDDF2\uD83C\uDDF9"),

        // ── Caucasus & Central Asia ─────────────────────────────────────
        CityTimezone("Tbilisi", "Georgia", "Asia/Tbilisi", "\uD83C\uDDEC\uD83C\uDDEA"),
        CityTimezone("Yerevan", "Armenia", "Asia/Yerevan", "\uD83C\uDDE6\uD83C\uDDF2"),
        CityTimezone("Baku", "Azerbaijan", "Asia/Baku", "\uD83C\uDDE6\uD83C\uDDFF"),
        CityTimezone("Astana", "Kazakhstan", "Asia/Almaty", "\uD83C\uDDF0\uD83C\uDDFF"),
        CityTimezone("Almaty", "Kazakhstan", "Asia/Almaty", "\uD83C\uDDF0\uD83C\uDDFF"),
        CityTimezone("Tashkent", "Uzbekistan", "Asia/Tashkent", "\uD83C\uDDFA\uD83C\uDDFF"),
        CityTimezone("Bishkek", "Kyrgyzstan", "Asia/Bishkek", "\uD83C\uDDF0\uD83C\uDDEC"),
        CityTimezone("Dushanbe", "Tajikistan", "Asia/Dushanbe", "\uD83C\uDDF9\uD83C\uDDEF"),
        CityTimezone("Ashgabat", "Turkmenistan", "Asia/Ashgabat", "\uD83C\uDDF9\uD83C\uDDF2"),

        // ── Middle East ─────────────────────────────────────────────────
        CityTimezone("Dubai", "United Arab Emirates", "Asia/Dubai", "\uD83C\uDDE6\uD83C\uDDEA"),
        CityTimezone("Abu Dhabi", "United Arab Emirates", "Asia/Dubai", "\uD83C\uDDE6\uD83C\uDDEA"),
        CityTimezone("Riyadh", "Saudi Arabia", "Asia/Riyadh", "\uD83C\uDDF8\uD83C\uDDE6"),
        CityTimezone("Jeddah", "Saudi Arabia", "Asia/Riyadh", "\uD83C\uDDF8\uD83C\uDDE6"),
        CityTimezone("Mecca", "Saudi Arabia", "Asia/Riyadh", "\uD83C\uDDF8\uD83C\uDDE6"),
        CityTimezone("Doha", "Qatar", "Asia/Qatar", "\uD83C\uDDF6\uD83C\uDDE6"),
        CityTimezone("Kuwait City", "Kuwait", "Asia/Kuwait", "\uD83C\uDDF0\uD83C\uDDFC"),
        CityTimezone("Manama", "Bahrain", "Asia/Bahrain", "\uD83C\uDDE7\uD83C\uDDED"),
        CityTimezone("Muscat", "Oman", "Asia/Muscat", "\uD83C\uDDF4\uD83C\uDDF2"),
        CityTimezone("Baghdad", "Iraq", "Asia/Baghdad", "\uD83C\uDDEE\uD83C\uDDF6"),
        CityTimezone("Tehran", "Iran", "Asia/Tehran", "\uD83C\uDDEE\uD83C\uDDF7"),
        CityTimezone("Jerusalem", "Israel", "Asia/Jerusalem", "\uD83C\uDDEE\uD83C\uDDF1"),
        CityTimezone("Tel Aviv", "Israel", "Asia/Jerusalem", "\uD83C\uDDEE\uD83C\uDDF1"),
        CityTimezone("Amman", "Jordan", "Asia/Amman", "\uD83C\uDDEF\uD83C\uDDF4"),
        CityTimezone("Beirut", "Lebanon", "Asia/Beirut", "\uD83C\uDDF1\uD83C\uDDE7"),
        CityTimezone("Damascus", "Syria", "Asia/Damascus", "\uD83C\uDDF8\uD83C\uDDFE"),
        CityTimezone("Sanaa", "Yemen", "Asia/Aden", "\uD83C\uDDFE\uD83C\uDDEA"),
        CityTimezone("Kabul", "Afghanistan", "Asia/Kabul", "\uD83C\uDDE6\uD83C\uDDEB"),

        // ── South Asia ──────────────────────────────────────────────────
        CityTimezone("Mumbai", "India", "Asia/Kolkata", "\uD83C\uDDEE\uD83C\uDDF3"),
        CityTimezone("New Delhi", "India", "Asia/Kolkata", "\uD83C\uDDEE\uD83C\uDDF3"),
        CityTimezone("Bangalore", "India", "Asia/Kolkata", "\uD83C\uDDEE\uD83C\uDDF3"),
        CityTimezone("Chennai", "India", "Asia/Kolkata", "\uD83C\uDDEE\uD83C\uDDF3"),
        CityTimezone("Kolkata", "India", "Asia/Kolkata", "\uD83C\uDDEE\uD83C\uDDF3"),
        CityTimezone("Hyderabad", "India", "Asia/Kolkata", "\uD83C\uDDEE\uD83C\uDDF3"),
        CityTimezone("Ahmedabad", "India", "Asia/Kolkata", "\uD83C\uDDEE\uD83C\uDDF3"),
        CityTimezone("Pune", "India", "Asia/Kolkata", "\uD83C\uDDEE\uD83C\uDDF3"),
        CityTimezone("Jaipur", "India", "Asia/Kolkata", "\uD83C\uDDEE\uD83C\uDDF3"),
        CityTimezone("Goa", "India", "Asia/Kolkata", "\uD83C\uDDEE\uD83C\uDDF3"),
        CityTimezone("Islamabad", "Pakistan", "Asia/Karachi", "\uD83C\uDDF5\uD83C\uDDF0"),
        CityTimezone("Karachi", "Pakistan", "Asia/Karachi", "\uD83C\uDDF5\uD83C\uDDF0"),
        CityTimezone("Lahore", "Pakistan", "Asia/Karachi", "\uD83C\uDDF5\uD83C\uDDF0"),
        CityTimezone("Dhaka", "Bangladesh", "Asia/Dhaka", "\uD83C\uDDE7\uD83C\uDDE9"),
        CityTimezone("Colombo", "Sri Lanka", "Asia/Colombo", "\uD83C\uDDF1\uD83C\uDDF0"),
        CityTimezone("Kathmandu", "Nepal", "Asia/Kathmandu", "\uD83C\uDDF3\uD83C\uDDF5"),
        CityTimezone("Thimphu", "Bhutan", "Asia/Thimphu", "\uD83C\uDDE7\uD83C\uDDF9"),
        CityTimezone("Male", "Maldives", "Indian/Maldives", "\uD83C\uDDF2\uD83C\uDDFB"),

        // ── East Asia ───────────────────────────────────────────────────
        CityTimezone("Tokyo", "Japan", "Asia/Tokyo", "\uD83C\uDDEF\uD83C\uDDF5"),
        CityTimezone("Osaka", "Japan", "Asia/Tokyo", "\uD83C\uDDEF\uD83C\uDDF5"),
        CityTimezone("Kyoto", "Japan", "Asia/Tokyo", "\uD83C\uDDEF\uD83C\uDDF5"),
        CityTimezone("Beijing", "China", "Asia/Shanghai", "\uD83C\uDDE8\uD83C\uDDF3"),
        CityTimezone("Shanghai", "China", "Asia/Shanghai", "\uD83C\uDDE8\uD83C\uDDF3"),
        CityTimezone("Shenzhen", "China", "Asia/Shanghai", "\uD83C\uDDE8\uD83C\uDDF3"),
        CityTimezone("Guangzhou", "China", "Asia/Shanghai", "\uD83C\uDDE8\uD83C\uDDF3"),
        CityTimezone("Chengdu", "China", "Asia/Shanghai", "\uD83C\uDDE8\uD83C\uDDF3"),
        CityTimezone("Xi'an", "China", "Asia/Shanghai", "\uD83C\uDDE8\uD83C\uDDF3"),
        CityTimezone("Hong Kong", "China", "Asia/Hong_Kong", "\uD83C\uDDED\uD83C\uDDF0"),
        CityTimezone("Macau", "China", "Asia/Macau", "\uD83C\uDDF2\uD83C\uDDF4"),
        CityTimezone("Taipei", "Taiwan", "Asia/Taipei", "\uD83C\uDDF9\uD83C\uDDFC"),
        CityTimezone("Seoul", "South Korea", "Asia/Seoul", "\uD83C\uDDF0\uD83C\uDDF7"),
        CityTimezone("Busan", "South Korea", "Asia/Seoul", "\uD83C\uDDF0\uD83C\uDDF7"),
        CityTimezone("Pyongyang", "North Korea", "Asia/Pyongyang", "\uD83C\uDDF0\uD83C\uDDF5"),
        CityTimezone("Ulaanbaatar", "Mongolia", "Asia/Ulaanbaatar", "\uD83C\uDDF2\uD83C\uDDF3"),

        // ── Southeast Asia ──────────────────────────────────────────────
        CityTimezone("Bangkok", "Thailand", "Asia/Bangkok", "\uD83C\uDDF9\uD83C\uDDED"),
        CityTimezone("Phuket", "Thailand", "Asia/Bangkok", "\uD83C\uDDF9\uD83C\uDDED"),
        CityTimezone("Chiang Mai", "Thailand", "Asia/Bangkok", "\uD83C\uDDF9\uD83C\uDDED"),
        CityTimezone("Singapore", "Singapore", "Asia/Singapore", "\uD83C\uDDF8\uD83C\uDDEC"),
        CityTimezone("Kuala Lumpur", "Malaysia", "Asia/Kuala_Lumpur", "\uD83C\uDDF2\uD83C\uDDFE"),
        CityTimezone("Jakarta", "Indonesia", "Asia/Jakarta", "\uD83C\uDDEE\uD83C\uDDE9"),
        CityTimezone("Surabaya", "Indonesia", "Asia/Jakarta", "\uD83C\uDDEE\uD83C\uDDE9"),
        CityTimezone("Makassar", "Indonesia", "Asia/Makassar", "\uD83C\uDDEE\uD83C\uDDE9"),
        CityTimezone("Jayapura", "Indonesia", "Asia/Jayapura", "\uD83C\uDDEE\uD83C\uDDE9"),
        CityTimezone("Manila", "Philippines", "Asia/Manila", "\uD83C\uDDF5\uD83C\uDDED"),
        CityTimezone("Cebu", "Philippines", "Asia/Manila", "\uD83C\uDDF5\uD83C\uDDED"),
        CityTimezone("Hanoi", "Vietnam", "Asia/Ho_Chi_Minh", "\uD83C\uDDFB\uD83C\uDDF3"),
        CityTimezone("Ho Chi Minh City", "Vietnam", "Asia/Ho_Chi_Minh", "\uD83C\uDDFB\uD83C\uDDF3"),
        CityTimezone("Phnom Penh", "Cambodia", "Asia/Phnom_Penh", "\uD83C\uDDF0\uD83C\uDDED"),
        CityTimezone("Siem Reap", "Cambodia", "Asia/Phnom_Penh", "\uD83C\uDDF0\uD83C\uDDED"),
        CityTimezone("Vientiane", "Laos", "Asia/Vientiane", "\uD83C\uDDF1\uD83C\uDDE6"),
        CityTimezone("Yangon", "Myanmar", "Asia/Yangon", "\uD83C\uDDF2\uD83C\uDDF2"),
        CityTimezone("Naypyidaw", "Myanmar", "Asia/Yangon", "\uD83C\uDDF2\uD83C\uDDF2"),
        CityTimezone("Bandar Seri Begawan", "Brunei", "Asia/Brunei", "\uD83C\uDDE7\uD83C\uDDF3"),
        CityTimezone("Dili", "Timor-Leste", "Asia/Dili", "\uD83C\uDDF9\uD83C\uDDF1"),

        // ── Africa — North ──────────────────────────────────────────────
        CityTimezone("Cairo", "Egypt", "Africa/Cairo", "\uD83C\uDDEA\uD83C\uDDEC"),
        CityTimezone("Alexandria", "Egypt", "Africa/Cairo", "\uD83C\uDDEA\uD83C\uDDEC"),
        CityTimezone("Tripoli", "Libya", "Africa/Tripoli", "\uD83C\uDDF1\uD83C\uDDFE"),
        CityTimezone("Tunis", "Tunisia", "Africa/Tunis", "\uD83C\uDDF9\uD83C\uDDF3"),
        CityTimezone("Algiers", "Algeria", "Africa/Algiers", "\uD83C\uDDE9\uD83C\uDDFF"),
        CityTimezone("Rabat", "Morocco", "Africa/Casablanca", "\uD83C\uDDF2\uD83C\uDDE6"),
        CityTimezone("Casablanca", "Morocco", "Africa/Casablanca", "\uD83C\uDDF2\uD83C\uDDE6"),
        CityTimezone("Marrakech", "Morocco", "Africa/Casablanca", "\uD83C\uDDF2\uD83C\uDDE6"),
        CityTimezone("Nouakchott", "Mauritania", "Africa/Nouakchott", "\uD83C\uDDF2\uD83C\uDDF7"),
        CityTimezone("Khartoum", "Sudan", "Africa/Khartoum", "\uD83C\uDDF8\uD83C\uDDE9"),
        CityTimezone("Juba", "South Sudan", "Africa/Juba", "\uD83C\uDDF8\uD83C\uDDF8"),

        // ── Africa — West ───────────────────────────────────────────────
        CityTimezone("Lagos", "Nigeria", "Africa/Lagos", "\uD83C\uDDF3\uD83C\uDDEC"),
        CityTimezone("Abuja", "Nigeria", "Africa/Lagos", "\uD83C\uDDF3\uD83C\uDDEC"),
        CityTimezone("Accra", "Ghana", "Africa/Accra", "\uD83C\uDDEC\uD83C\uDDED"),
        CityTimezone("Dakar", "Senegal", "Africa/Dakar", "\uD83C\uDDF8\uD83C\uDDF3"),
        CityTimezone("Abidjan", "Ivory Coast", "Africa/Abidjan", "\uD83C\uDDE8\uD83C\uDDEE"),
        CityTimezone("Bamako", "Mali", "Africa/Bamako", "\uD83C\uDDF2\uD83C\uDDF1"),
        CityTimezone("Ouagadougou", "Burkina Faso", "Africa/Ouagadougou", "\uD83C\uDDE7\uD83C\uDDEB"),
        CityTimezone("Niamey", "Niger", "Africa/Niamey", "\uD83C\uDDF3\uD83C\uDDEA"),
        CityTimezone("Conakry", "Guinea", "Africa/Conakry", "\uD83C\uDDEC\uD83C\uDDF3"),
        CityTimezone("Freetown", "Sierra Leone", "Africa/Freetown", "\uD83C\uDDF8\uD83C\uDDF1"),
        CityTimezone("Monrovia", "Liberia", "Africa/Monrovia", "\uD83C\uDDF1\uD83C\uDDF7"),
        CityTimezone("Lome", "Togo", "Africa/Lome", "\uD83C\uDDF9\uD83C\uDDEC"),
        CityTimezone("Cotonou", "Benin", "Africa/Porto-Novo", "\uD83C\uDDE7\uD83C\uDDEF"),
        CityTimezone("Porto-Novo", "Benin", "Africa/Porto-Novo", "\uD83C\uDDE7\uD83C\uDDEF"),
        CityTimezone("Banjul", "Gambia", "Africa/Banjul", "\uD83C\uDDEC\uD83C\uDDF2"),
        CityTimezone("Bissau", "Guinea-Bissau", "Africa/Bissau", "\uD83C\uDDEC\uD83C\uDDFC"),
        CityTimezone("Praia", "Cape Verde", "Atlantic/Cape_Verde", "\uD83C\uDDE8\uD83C\uDDFB"),

        // ── Africa — East ───────────────────────────────────────────────
        CityTimezone("Nairobi", "Kenya", "Africa/Nairobi", "\uD83C\uDDF0\uD83C\uDDEA"),
        CityTimezone("Addis Ababa", "Ethiopia", "Africa/Addis_Ababa", "\uD83C\uDDEA\uD83C\uDDF9"),
        CityTimezone("Dar es Salaam", "Tanzania", "Africa/Dar_es_Salaam", "\uD83C\uDDF9\uD83C\uDDFF"),
        CityTimezone("Dodoma", "Tanzania", "Africa/Dar_es_Salaam", "\uD83C\uDDF9\uD83C\uDDFF"),
        CityTimezone("Kampala", "Uganda", "Africa/Kampala", "\uD83C\uDDFA\uD83C\uDDEC"),
        CityTimezone("Kigali", "Rwanda", "Africa/Kigali", "\uD83C\uDDF7\uD83C\uDDFC"),
        CityTimezone("Bujumbura", "Burundi", "Africa/Bujumbura", "\uD83C\uDDE7\uD83C\uDDEE"),
        CityTimezone("Mogadishu", "Somalia", "Africa/Mogadishu", "\uD83C\uDDF8\uD83C\uDDF4"),
        CityTimezone("Djibouti", "Djibouti", "Africa/Djibouti", "\uD83C\uDDE9\uD83C\uDDEF"),
        CityTimezone("Asmara", "Eritrea", "Africa/Asmara", "\uD83C\uDDEA\uD83C\uDDF7"),
        CityTimezone("Zanzibar", "Tanzania", "Africa/Dar_es_Salaam", "\uD83C\uDDF9\uD83C\uDDFF"),

        // ── Africa — Central ────────────────────────────────────────────
        CityTimezone("Kinshasa", "DR Congo", "Africa/Kinshasa", "\uD83C\uDDE8\uD83C\uDDE9"),
        CityTimezone("Lubumbashi", "DR Congo", "Africa/Lubumbashi", "\uD83C\uDDE8\uD83C\uDDE9"),
        CityTimezone("Brazzaville", "Congo", "Africa/Brazzaville", "\uD83C\uDDE8\uD83C\uDDEC"),
        CityTimezone("Yaounde", "Cameroon", "Africa/Douala", "\uD83C\uDDE8\uD83C\uDDF2"),
        CityTimezone("Douala", "Cameroon", "Africa/Douala", "\uD83C\uDDE8\uD83C\uDDF2"),
        CityTimezone("Libreville", "Gabon", "Africa/Libreville", "\uD83C\uDDEC\uD83C\uDDE6"),
        CityTimezone("Bangui", "Central African Republic", "Africa/Bangui", "\uD83C\uDDE8\uD83C\uDDEB"),
        CityTimezone("N'Djamena", "Chad", "Africa/Ndjamena", "\uD83C\uDDF9\uD83C\uDDE9"),
        CityTimezone("Malabo", "Equatorial Guinea", "Africa/Malabo", "\uD83C\uDDEC\uD83C\uDDF6"),
        CityTimezone("Sao Tome", "Sao Tome and Principe", "Africa/Sao_Tome", "\uD83C\uDDF8\uD83C\uDDF9"),

        // ── Africa — Southern ───────────────────────────────────────────
        CityTimezone("Johannesburg", "South Africa", "Africa/Johannesburg", "\uD83C\uDDFF\uD83C\uDDE6"),
        CityTimezone("Cape Town", "South Africa", "Africa/Johannesburg", "\uD83C\uDDFF\uD83C\uDDE6"),
        CityTimezone("Pretoria", "South Africa", "Africa/Johannesburg", "\uD83C\uDDFF\uD83C\uDDE6"),
        CityTimezone("Durban", "South Africa", "Africa/Johannesburg", "\uD83C\uDDFF\uD83C\uDDE6"),
        CityTimezone("Maputo", "Mozambique", "Africa/Maputo", "\uD83C\uDDF2\uD83C\uDDFF"),
        CityTimezone("Lusaka", "Zambia", "Africa/Lusaka", "\uD83C\uDDFF\uD83C\uDDF2"),
        CityTimezone("Harare", "Zimbabwe", "Africa/Harare", "\uD83C\uDDFF\uD83C\uDDFC"),
        CityTimezone("Lilongwe", "Malawi", "Africa/Blantyre", "\uD83C\uDDF2\uD83C\uDDFC"),
        CityTimezone("Windhoek", "Namibia", "Africa/Windhoek", "\uD83C\uDDF3\uD83C\uDDE6"),
        CityTimezone("Gaborone", "Botswana", "Africa/Gaborone", "\uD83C\uDDE7\uD83C\uDDFC"),
        CityTimezone("Mbabane", "Eswatini", "Africa/Mbabane", "\uD83C\uDDF8\uD83C\uDDFF"),
        CityTimezone("Maseru", "Lesotho", "Africa/Maseru", "\uD83C\uDDF1\uD83C\uDDF8"),
        CityTimezone("Antananarivo", "Madagascar", "Indian/Antananarivo", "\uD83C\uDDF2\uD83C\uDDEC"),
        CityTimezone("Port Louis", "Mauritius", "Indian/Mauritius", "\uD83C\uDDF2\uD83C\uDDFA"),
        CityTimezone("Victoria", "Seychelles", "Indian/Mahe", "\uD83C\uDDF8\uD83C\uDDE8"),
        CityTimezone("Moroni", "Comoros", "Indian/Comoro", "\uD83C\uDDF0\uD83C\uDDF2"),
        CityTimezone("Luanda", "Angola", "Africa/Luanda", "\uD83C\uDDE6\uD83C\uDDF4"),

        // ── Oceania — Australia ──────────────────────────────────────────
        CityTimezone("Sydney", "Australia", "Australia/Sydney", "\uD83C\uDDE6\uD83C\uDDFA"),
        CityTimezone("Melbourne", "Australia", "Australia/Melbourne", "\uD83C\uDDE6\uD83C\uDDFA"),
        CityTimezone("Brisbane", "Australia", "Australia/Brisbane", "\uD83C\uDDE6\uD83C\uDDFA"),
        CityTimezone("Perth", "Australia", "Australia/Perth", "\uD83C\uDDE6\uD83C\uDDFA"),
        CityTimezone("Adelaide", "Australia", "Australia/Adelaide", "\uD83C\uDDE6\uD83C\uDDFA"),
        CityTimezone("Canberra", "Australia", "Australia/Sydney", "\uD83C\uDDE6\uD83C\uDDFA"),
        CityTimezone("Hobart", "Australia", "Australia/Hobart", "\uD83C\uDDE6\uD83C\uDDFA"),
        CityTimezone("Darwin", "Australia", "Australia/Darwin", "\uD83C\uDDE6\uD83C\uDDFA"),
        CityTimezone("Gold Coast", "Australia", "Australia/Brisbane", "\uD83C\uDDE6\uD83C\uDDFA"),
        CityTimezone("Lord Howe Island", "Australia", "Australia/Lord_Howe", "\uD83C\uDDE6\uD83C\uDDFA"),
        CityTimezone("Eucla", "Australia", "Australia/Eucla", "\uD83C\uDDE6\uD83C\uDDFA"),

        // ── Oceania — New Zealand & Pacific ─────────────────────────────
        CityTimezone("Auckland", "New Zealand", "Pacific/Auckland", "\uD83C\uDDF3\uD83C\uDDFF"),
        CityTimezone("Wellington", "New Zealand", "Pacific/Auckland", "\uD83C\uDDF3\uD83C\uDDFF"),
        CityTimezone("Christchurch", "New Zealand", "Pacific/Auckland", "\uD83C\uDDF3\uD83C\uDDFF"),
        CityTimezone("Chatham Islands", "New Zealand", "Pacific/Chatham", "\uD83C\uDDF3\uD83C\uDDFF"),
        CityTimezone("Suva", "Fiji", "Pacific/Fiji", "\uD83C\uDDEB\uD83C\uDDEF"),
        CityTimezone("Port Moresby", "Papua New Guinea", "Pacific/Port_Moresby", "\uD83C\uDDF5\uD83C\uDDEC"),
        CityTimezone("Noumea", "New Caledonia", "Pacific/Noumea", "\uD83C\uDDF3\uD83C\uDDE8"),
        CityTimezone("Honiara", "Solomon Islands", "Pacific/Guadalcanal", "\uD83C\uDDF8\uD83C\uDDE7"),
        CityTimezone("Port Vila", "Vanuatu", "Pacific/Efate", "\uD83C\uDDFB\uD83C\uDDFA"),
        CityTimezone("Apia", "Samoa", "Pacific/Apia", "\uD83C\uDDFC\uD83C\uDDF8"),
        CityTimezone("Nuku'alofa", "Tonga", "Pacific/Tongatapu", "\uD83C\uDDF9\uD83C\uDDF4"),
        CityTimezone("Tarawa", "Kiribati", "Pacific/Tarawa", "\uD83C\uDDF0\uD83C\uDDEE"),
        CityTimezone("Funafuti", "Tuvalu", "Pacific/Funafuti", "\uD83C\uDDF9\uD83C\uDDFB"),
        CityTimezone("Majuro", "Marshall Islands", "Pacific/Majuro", "\uD83C\uDDF2\uD83C\uDDED"),
        CityTimezone("Palikir", "Micronesia", "Pacific/Pohnpei", "\uD83C\uDDEB\uD83C\uDDF2"),
        CityTimezone("Koror", "Palau", "Pacific/Palau", "\uD83C\uDDF5\uD83C\uDDFC"),
        CityTimezone("Nauru", "Nauru", "Pacific/Nauru", "\uD83C\uDDF3\uD83C\uDDF7"),
        CityTimezone("Pago Pago", "American Samoa", "Pacific/Pago_Pago", "\uD83C\uDDE6\uD83C\uDDF8"),
        CityTimezone("Rarotonga", "Cook Islands", "Pacific/Rarotonga", "\uD83C\uDDE8\uD83C\uDDF0"),
        CityTimezone("Niue", "Niue", "Pacific/Niue", "\uD83C\uDDF3\uD83C\uDDFA"),
        CityTimezone("Tahiti", "French Polynesia", "Pacific/Tahiti", "\uD83C\uDDF5\uD83C\uDDEB"),
        CityTimezone("Guam", "Guam", "Pacific/Guam", "\uD83C\uDDEC\uD83C\uDDFA"),
        CityTimezone("Midway", "United States", "Pacific/Midway", "\uD83C\uDDFA\uD83C\uDDF8"),
        CityTimezone("Kiritimati", "Kiribati", "Pacific/Kiritimati", "\uD83C\uDDF0\uD83C\uDDEE"),
        CityTimezone("Norfolk Island", "Australia", "Pacific/Norfolk", "\uD83C\uDDF3\uD83C\uDDEB"),
        CityTimezone("Fakaofo", "Tokelau", "Pacific/Fakaofo", "\uD83C\uDDF9\uD83C\uDDF0"),

        // ── Atlantic & Indian Ocean Islands ─────────────────────────────
        CityTimezone("Bermuda", "Bermuda", "Atlantic/Bermuda", "\uD83C\uDDE7\uD83C\uDDF2"),
        CityTimezone("Azores", "Portugal", "Atlantic/Azores", "\uD83C\uDDF5\uD83C\uDDF9"),
        CityTimezone("Canary Islands", "Spain", "Atlantic/Canary", "\uD83C\uDDEA\uD83C\uDDF8"),
        CityTimezone("Reunion", "France", "Indian/Reunion", "\uD83C\uDDF7\uD83C\uDDEA"),

        // ── Half-hour & quarter-hour offset zones ───────────────────────
        // India (UTC+5:30) — already included above (Mumbai, New Delhi, etc.)
        // Nepal (UTC+5:45) — Kathmandu already included above
        // Iran (UTC+3:30) — Tehran already included above
        // Afghanistan (UTC+4:30) — Kabul already included above
        // Myanmar (UTC+6:30) — Yangon already included above
        // Sri Lanka (UTC+5:30) — Colombo already included above
        // Australia/Adelaide (UTC+9:30) — already included above
        // Australia/Darwin (UTC+9:30) — already included above
        // Australia/Lord_Howe (UTC+10:30) — already included above
        // Australia/Eucla (UTC+8:45) — already included above
        // Chatham Islands (UTC+12:45) — already included above
        // Marquesas Islands (UTC-9:30)
        CityTimezone("Marquesas Islands", "French Polynesia", "Pacific/Marquesas", "\uD83C\uDDF5\uD83C\uDDEB"),
        // Newfoundland (UTC-3:30) — St. John's already included above
        // Cocos Islands (UTC+6:30)
        CityTimezone("Cocos Islands", "Australia", "Indian/Cocos", "\uD83C\uDDE8\uD83C\uDDE8"),

        // ── Additional world capitals & major cities ────────────────────
        CityTimezone("Ngerulmud", "Palau", "Pacific/Palau", "\uD83C\uDDF5\uD83C\uDDFC"),
        CityTimezone("Yaren", "Nauru", "Pacific/Nauru", "\uD83C\uDDF3\uD83C\uDDF7"),
        CityTimezone("South Tarawa", "Kiribati", "Pacific/Tarawa", "\uD83C\uDDF0\uD83C\uDDEE"),
        CityTimezone("Saipan", "Northern Mariana Islands", "Pacific/Guam", "\uD83C\uDDF2\uD83C\uDDF5"),
        CityTimezone("Vatican City", "Vatican City", "Europe/Vatican", "\uD83C\uDDFB\uD83C\uDDE6"),
        CityTimezone("Gibraltar", "Gibraltar", "Europe/Gibraltar", "\uD83C\uDDEC\uD83C\uDDEE"),

        // ── Popular travel destinations & additional coverage ───────────
        CityTimezone("Santorini", "Greece", "Europe/Athens", "\uD83C\uDDEC\uD83C\uDDF7"),
        CityTimezone("Mykonos", "Greece", "Europe/Athens", "\uD83C\uDDEC\uD83C\uDDF7"),
        CityTimezone("Dubrovnik", "Croatia", "Europe/Zagreb", "\uD83C\uDDED\uD83C\uDDF7"),
        CityTimezone("Ibiza", "Spain", "Europe/Madrid", "\uD83C\uDDEA\uD83C\uDDF8"),
        CityTimezone("Palma de Mallorca", "Spain", "Europe/Madrid", "\uD83C\uDDEA\uD83C\uDDF8"),
        CityTimezone("Tenerife", "Spain", "Atlantic/Canary", "\uD83C\uDDEA\uD83C\uDDF8"),
        CityTimezone("Antalya", "Turkey", "Europe/Istanbul", "\uD83C\uDDF9\uD83C\uDDF7"),
        CityTimezone("Bodrum", "Turkey", "Europe/Istanbul", "\uD83C\uDDF9\uD83C\uDDF7"),
        CityTimezone("Petra", "Jordan", "Asia/Amman", "\uD83C\uDDEF\uD83C\uDDF4"),
        CityTimezone("Machu Picchu", "Peru", "America/Lima", "\uD83C\uDDF5\uD83C\uDDEA"),
        CityTimezone("Cusco", "Peru", "America/Lima", "\uD83C\uDDF5\uD83C\uDDEA"),
        CityTimezone("Maldives", "Maldives", "Indian/Maldives", "\uD83C\uDDF2\uD83C\uDDFB"),
        CityTimezone("Mauritius", "Mauritius", "Indian/Mauritius", "\uD83C\uDDF2\uD83C\uDDFA"),
        CityTimezone("Seychelles", "Seychelles", "Indian/Mahe", "\uD83C\uDDF8\uD83C\uDDE8"),
        CityTimezone("Lhasa", "China", "Asia/Shanghai", "\uD83C\uDDE8\uD83C\uDDF3"),
        CityTimezone("Urumqi", "China", "Asia/Urumqi", "\uD83C\uDDE8\uD83C\uDDF3"),
        CityTimezone("Sapporo", "Japan", "Asia/Tokyo", "\uD83C\uDDEF\uD83C\uDDF5"),
        CityTimezone("Okinawa", "Japan", "Asia/Tokyo", "\uD83C\uDDEF\uD83C\uDDF5"),
        CityTimezone("Jeju", "South Korea", "Asia/Seoul", "\uD83C\uDDF0\uD83C\uDDF7"),
        CityTimezone("Lombok", "Indonesia", "Asia/Makassar", "\uD83C\uDDEE\uD83C\uDDE9"),
        CityTimezone("Yogyakarta", "Indonesia", "Asia/Jakarta", "\uD83C\uDDEE\uD83C\uDDE9"),
        CityTimezone("Penang", "Malaysia", "Asia/Kuala_Lumpur", "\uD83C\uDDF2\uD83C\uDDFE"),
        CityTimezone("Langkawi", "Malaysia", "Asia/Kuala_Lumpur", "\uD83C\uDDF2\uD83C\uDDFE"),
        CityTimezone("Boracay", "Philippines", "Asia/Manila", "\uD83C\uDDF5\uD83C\uDDED"),
        CityTimezone("Koh Samui", "Thailand", "Asia/Bangkok", "\uD83C\uDDF9\uD83C\uDDED"),
        CityTimezone("Da Nang", "Vietnam", "Asia/Ho_Chi_Minh", "\uD83C\uDDFB\uD83C\uDDF3"),
        CityTimezone("Luang Prabang", "Laos", "Asia/Vientiane", "\uD83C\uDDF1\uD83C\uDDE6"),
        CityTimezone("Haneda", "Japan", "Asia/Tokyo", "\uD83C\uDDEF\uD83C\uDDF5"),
        CityTimezone("Narita", "Japan", "Asia/Tokyo", "\uD83C\uDDEF\uD83C\uDDF5"),
        CityTimezone("Agra", "India", "Asia/Kolkata", "\uD83C\uDDEE\uD83C\uDDF3"),
        CityTimezone("Varanasi", "India", "Asia/Kolkata", "\uD83C\uDDEE\uD83C\uDDF3"),
        CityTimezone("Udaipur", "India", "Asia/Kolkata", "\uD83C\uDDEE\uD83C\uDDF3"),
        CityTimezone("Rishikesh", "India", "Asia/Kolkata", "\uD83C\uDDEE\uD83C\uDDF3"),
        CityTimezone("Cape Verde", "Cape Verde", "Atlantic/Cape_Verde", "\uD83C\uDDE8\uD83C\uDDFB"),
        CityTimezone("Fez", "Morocco", "Africa/Casablanca", "\uD83C\uDDF2\uD83C\uDDE6"),
        CityTimezone("Luxor", "Egypt", "Africa/Cairo", "\uD83C\uDDEA\uD83C\uDDEC"),
        CityTimezone("Kilimanjaro", "Tanzania", "Africa/Dar_es_Salaam", "\uD83C\uDDF9\uD83C\uDDFF"),
        CityTimezone("Victoria Falls", "Zimbabwe", "Africa/Harare", "\uD83C\uDDFF\uD83C\uDDFC"),
        CityTimezone("Kruger", "South Africa", "Africa/Johannesburg", "\uD83C\uDDFF\uD83C\uDDE6"),
        CityTimezone("Bora Bora", "French Polynesia", "Pacific/Tahiti", "\uD83C\uDDF5\uD83C\uDDEB"),
        CityTimezone("Queenstown", "New Zealand", "Pacific/Auckland", "\uD83C\uDDF3\uD83C\uDDFF"),
        CityTimezone("Rotorua", "New Zealand", "Pacific/Auckland", "\uD83C\uDDF3\uD83C\uDDFF"),
        CityTimezone("Cairns", "Australia", "Australia/Brisbane", "\uD83C\uDDE6\uD83C\uDDFA"),
        CityTimezone("Great Barrier Reef", "Australia", "Australia/Brisbane", "\uD83C\uDDE6\uD83C\uDDFA"),
        CityTimezone("Uluru", "Australia", "Australia/Darwin", "\uD83C\uDDE6\uD83C\uDDFA"),

        // ── Additional US cities ────────────────────────────────────────
        CityTimezone("Austin", "United States", "America/Chicago", "\uD83C\uDDFA\uD83C\uDDF8"),
        CityTimezone("San Jose", "United States", "America/Los_Angeles", "\uD83C\uDDFA\uD83C\uDDF8"),
        CityTimezone("Charlotte", "United States", "America/New_York", "\uD83C\uDDFA\uD83C\uDDF8"),
        CityTimezone("Orlando", "United States", "America/New_York", "\uD83C\uDDFA\uD83C\uDDF8"),
        CityTimezone("New Orleans", "United States", "America/Chicago", "\uD83C\uDDFA\uD83C\uDDF8"),
        CityTimezone("Tampa", "United States", "America/New_York", "\uD83C\uDDFA\uD83C\uDDF8"),
        CityTimezone("Pittsburgh", "United States", "America/New_York", "\uD83C\uDDFA\uD83C\uDDF8"),
        CityTimezone("Raleigh", "United States", "America/New_York", "\uD83C\uDDFA\uD83C\uDDF8"),
        CityTimezone("Omaha", "United States", "America/Chicago", "\uD83C\uDDFA\uD83C\uDDF8"),
        CityTimezone("Provo", "United States", "America/Denver", "\uD83C\uDDFA\uD83C\uDDF8"),
        CityTimezone("Boise", "United States", "America/Boise", "\uD83C\uDDFA\uD83C\uDDF8"),

        // ── Additional European cities ──────────────────────────────────
        CityTimezone("Cologne", "Germany", "Europe/Berlin", "\uD83C\uDDE9\uD83C\uDDEA"),
        CityTimezone("Gothenburg", "Sweden", "Europe/Stockholm", "\uD83C\uDDF8\uD83C\uDDEA"),
        CityTimezone("Malmo", "Sweden", "Europe/Stockholm", "\uD83C\uDDF8\uD83C\uDDEA"),
        CityTimezone("Bergen", "Norway", "Europe/Oslo", "\uD83C\uDDF3\uD83C\uDDF4"),
        CityTimezone("Turku", "Finland", "Europe/Helsinki", "\uD83C\uDDEB\uD83C\uDDEE"),
        CityTimezone("Gdansk", "Poland", "Europe/Warsaw", "\uD83C\uDDF5\uD83C\uDDF1"),
        CityTimezone("Split", "Croatia", "Europe/Zagreb", "\uD83C\uDDED\uD83C\uDDF7"),
        CityTimezone("Seville", "Spain", "Europe/Madrid", "\uD83C\uDDEA\uD83C\uDDF8"),
        CityTimezone("Valencia", "Spain", "Europe/Madrid", "\uD83C\uDDEA\uD83C\uDDF8"),
        CityTimezone("Salzburg", "Austria", "Europe/Vienna", "\uD83C\uDDE6\uD83C\uDDF9"),
        CityTimezone("Innsbruck", "Austria", "Europe/Vienna", "\uD83C\uDDE6\uD83C\uDDF9"),
        CityTimezone("Bruges", "Belgium", "Europe/Brussels", "\uD83C\uDDE7\uD83C\uDDEA"),

        // ── Additional Asian cities ─────────────────────────────────────
        CityTimezone("Nara", "Japan", "Asia/Tokyo", "\uD83C\uDDEF\uD83C\uDDF5"),
        CityTimezone("Hiroshima", "Japan", "Asia/Tokyo", "\uD83C\uDDEF\uD83C\uDDF5"),
        CityTimezone("Hangzhou", "China", "Asia/Shanghai", "\uD83C\uDDE8\uD83C\uDDF3"),
        CityTimezone("Suzhou", "China", "Asia/Shanghai", "\uD83C\uDDE8\uD83C\uDDF3"),
        CityTimezone("Nanjing", "China", "Asia/Shanghai", "\uD83C\uDDE8\uD83C\uDDF3"),
        CityTimezone("Wuhan", "China", "Asia/Shanghai", "\uD83C\uDDE8\uD83C\uDDF3"),
        CityTimezone("Guilin", "China", "Asia/Shanghai", "\uD83C\uDDE8\uD83C\uDDF3"),
        CityTimezone("Incheon", "South Korea", "Asia/Seoul", "\uD83C\uDDF0\uD83C\uDDF7"),
        CityTimezone("Kochi", "India", "Asia/Kolkata", "\uD83C\uDDEE\uD83C\uDDF3"),
        CityTimezone("Amritsar", "India", "Asia/Kolkata", "\uD83C\uDDEE\uD83C\uDDF3")
    )

    /**
     * Searches cities by name or country. Results are ranked:
     * 1. City names starting with the query (prefix match)
     * 2. Country names starting with the query
     * 3. City names containing the query (substring match)
     * 4. Country names containing the query
     *
     * Within each group, original list order is preserved.
     * All matching is case-insensitive.
     */
    fun search(query: String): List<CityTimezone> {
        if (query.isBlank()) return emptyList()

        val lowerQuery = query.trim().lowercase()

        val cityPrefixMatches = mutableListOf<CityTimezone>()
        val countryPrefixMatches = mutableListOf<CityTimezone>()
        val cityContainsMatches = mutableListOf<CityTimezone>()
        val countryContainsMatches = mutableListOf<CityTimezone>()

        for (entry in cities) {
            val lowerCity = entry.city.lowercase()
            val lowerCountry = entry.country.lowercase()

            when {
                lowerCity.startsWith(lowerQuery) -> cityPrefixMatches.add(entry)
                lowerCountry.startsWith(lowerQuery) -> countryPrefixMatches.add(entry)
                lowerCity.contains(lowerQuery) -> cityContainsMatches.add(entry)
                lowerCountry.contains(lowerQuery) -> countryContainsMatches.add(entry)
            }
        }

        return cityPrefixMatches + countryPrefixMatches + cityContainsMatches + countryContainsMatches
    }
}
