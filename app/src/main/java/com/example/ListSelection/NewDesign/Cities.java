package com.example.ListSelection.NewDesign;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.example.ListSelection.R;

/* cities list and related methods */
public class Cities {
    public static String[] GetListData() {
        String[] cities = {
                "Abbotsford", // 1
                "Addington", // 2
                "Adelaide", // 3
                "Adjala", // 4
                "Admaston", // 5
                "AirRonge", // 6
                "Airdrie", // 7
                "Ajax", // 10
                "AlbertaBeach", // 11
                "Alberton", // 12
                "Alexander", // 13
                "Alfred",// 14
                "Algonquin", // 15
                "Allardville",// 16
                "Alnwick", // 17
                "Alonsa", // 18
                "Altona", // 19
                "Alvinston", // 20
                "Amaranth",// 21
                "Amherst", // 22
                "Amherstburg",// 23
                "Anmore", // 24
                "Antigonish", // 25
                "Arborg", // 26
                "Argyle",// 27
                "Armour", // 28
                "Armstrong", // 29
                "Arnprior", // 31
                "Arran-Elderslie", // 32
                "Arviat", // 33
                "Ashcroft", // 34
                "Ashfield",// 35
                "Assiginack", // 36
                "Assiniboia", // 37
                "Athabasca", // 38
                "Athens", // 39
                "Atholville", // 40
                "Atikokan",// 41
                "Augusta", // 42
                "Aurora", // 43
                "Aylmer", // 44
                "Baie", // 45
                "BakerLake", // 46
                "Balgonie", // 47
                "Balmoral", // 48
                "Bancroft", // 49
                "Banff", // 50
                "Barrhead", // 51
                "Barrie", // 52
                "Barriere", // 53
                "Barrington", // 54
                "Bassano", // 55
                "Bathurst", // 56
                "Battleford", // 57
                "Bayfield", // 58
                "Bayham", // 59
                "Beachburg", // 60
                "Beaubassin",// 61
                "Beaumont",// 62
                "Beausejour", // 63
                "BeaverRiver", // 64
                "Beaverlodge", // 65
                "Beckwith", // 66
                "Behchoko", // 67
                "Belledune", // 68
                "Belleville",  // 69
                "Bentley",  // 70
                "Beresford",  // 71
                "Bertrand",   // 72
                "Berwick",  // 73
                "Bifrost",  // 74
                "Biggar",  // 75
                "Birch",  // 76
                "BishopsFalls",  // 77
                "BlackDiamond",  // 78
                "BlackRiver",  // 79
                "Blackfalds", // 80
                "Blandford",// 81
                "BlindRiver", // 82
                "Blucher", // 83
                "Bluewater", // 84
                "Blumenort", // 85
                "Boissevain", // 86
                "Bonaccord", // 87
                "Bonavista", // 88
                "Bonfield", // 89
                "Bonnechere", // 90
                "Bonnyville", // 91
                "Botsford",  // 92
                "Botwood",  // 93
                "BowIsland",  // 94
                "Bowden",  // 95
                "BowenIsland",   // 96
                "Bracebridge",   // 97
                "Bradford",   // 98
                "Brampton",   // 99
                "Brandon",   // 100
                "Brant",  // 101
                "Brantford",   // 102
                "Brenda",   // 103
                "Breslau",   // 104
                "Bridgewater",   // 105
                "Bright",   // 106
                "Brighton",   // 107
                "Britannia",   // 108
                "Brockton",   // 109
                "Brockton",   // 110
                "Brockville",   // 111
                "Brokenhead",    // 112
                "Brooks",    // 113
                "Brudenell",    // 114
                "Bruederheim",    // 115
                "Buckland",    // 116
                "Buctouche",    // 117
                "Burford",    // 118
                "Burgeo",    // 119
                "Burin",    // 120
                "Burlington",   // 121
                "Burnaby",    // 122
                "Burton",    // 123
                "Caledon",    // 124
                "Calgary",    // 125
                "Callander",     // 126
                "Calmar",     // 127
                "Cambridge",    // 128
                "CambridgeBay",   // 129
                "CampbellRiver",   // 130
                "Campbellton",   // 131
                "Camrose", // 132
                "Canwood",  // 133
                "Carbonear",  // 134
                "Cardston",  // 135
                "Cardwell",   // 136
                "Carling",   // 137
                "Carlyle",    // 138
                "Carman",   // 139
                "Carnduff",   // 140
                "Carstairs",   // 141
                "Cartier",    // 142
                "Cartwright",    // 143
                "Casselman",    // 144
                "Castlegar",     // 145
                "Cavan",     // 146
                "Cedar",     // 147
                "Centreville",     // 148
                "Champlain",    // 149
                "Chapleau",     // 150
                "Charlo",    // 151
                "Charlottetown",     // 152
                "Chase",     // 153
                "Chatham",     // 154
                "Chatsworth",     // 155
                "Chester",     // 156
                "Chestermere",     // 157
                "Chetwynd",     // 158
                "Chilliwack",     // 159
                "Chipman",     // 160
                "Chisholm",     // 161
                "ChurchPoint",      // 162
                "Claremont",      // 163
                "Clarence",      // 164
                "Clarenville",      // 165
                "Claresholm",      // 166
                "Clarington",      // 167
                "Clearview",     // 168
                "Clearwater",     // 169
                "Coaldale",     // 170
                "Coalhurst",     // 171
                "Cobalt",      // 172
                "Cobourg",      // 173
                "Coldstream",     // 174
                "Coldwell",      // 175
                "Collingwood",      // 176
                "Colwood",      // 177
                "Comox",     // 178
                "Conestogo",      // 179
                "Coombs",      // 180
                "Coquitlam",    // 181
                "Cornwallis",    // 182
                "Courtenay",    // 183
                "Coverdale",    // 184
                "Cramahe",    // 185
                "Cranbrook",    // 186
                "Creighton",    // 187
                "Creston",    // 188
                "Crossfield",     // 189
                "Cumberland",    // 190
                "Dalhousie",    // 191
                "Dalmeny",     // 192
                "Dauphin",     // 193
                "Davidson",    // 194
                "Dawson",    // 195
                "Delisle",    // 196
                "Delta",     // 197
                "Denmark",     // 198
                "Deseronto",     // 199
                "Devon",    // 200
                "Didsbury",    // 201
                "Dieppe",    // 202
                "Digby",    // 203
                "Dildo",    // 204
                "Dorchester",    // 205
                "Douglas",     // 206
                "Dryden",    // 207
                "Duchess",    // 208
                "Duncan",    // 209
                "Dundas",    // 210
                "Dunsmuir",    // 211
                "Durham",    // 212
                "Dutton",    // 213
                "Edmonton",    // 214
                "Edmundston",    // 215
                "Edson",    // 216
                "Edwardsburgh",    // 217
                "ElkPoint",    // 218
                "Elkford",     // 219
                "Ellice",    // 220
                "Elliot",    // 221
                "Ellison",    // 222
                "Elton",    // 223
                "Emo",    // 224
                "Enderby",    // 225
                "Englehart",    // 226
                "Enniskillen",    // 227
                "Erin",    // 228
                "Errington",    // 229
                "Espanola",    // 230
                "Esquimalt",    // 231
                "Essa",    // 232
                "Esterhazy",    // 233
                "Estevan",    // 234
                "Eston",    // 235
                "Ethelbert",    // 236
                "Falher",    // 237
                "Falmouth",    // 238
                "Faraday",    // 239
                "Fernie",    // 240
                "Fisher",     // 241
                "Fortune",     // 242
                "FoxCreek",     // 243
                "Fredericton",     // 244
                "Fruitvale",     // 245
                "Gambo",     // 246
                "Gananoque",     // 247
                "Gander",     // 248
                "GeorgianBay",      // 249
                "GeorgianBluffs",      // 250
                "Georgina",      // 251
                "Gibbons",      // 252
                "GilbertPlains",      // 253
                "Gillam",      // 254
                "Gimli",      // 255
                "GjoaHaven",      // 256
                "Glenelg",      // 257
                "Glenella",      // 258
                "Glovertown",      // 259
                "Goderich",      // 260
                "Golden",     // 261
                "Gordon",     // 262
                "Goulds",     // 263
                "Grahamdale",     // 264
                "Grassland",     // 265
                "Gravelbourg",     // 266
                "Gravenhurst",     // 267
                "Greenstone",     // 268
                "Greenwich",     // 269
                "Grenfell",     // 270
                "Grey",     // 271
                "GreyHighlands",     // 272
                "Grimsby",     // 273
                "Grimshaw",     // 274
                "Grindrod",     // 275
                "Grunthal",     // 276
                "Guelph",     // 277
                "Halifax",     // 278
                "Hamilton",      // 279
                "Hamiota",      // 280
                "Hampton", // 281
                "Hanna",  // 282
                "Hanover",  // 283
                "Hanwell", // 284
                "Hardwicke",  // 285
                "Havelock",  // 286
                "Hawkesbury",  // 287
                "HayRiver",  // 288
                "Headingley",   // 289
                "Hearst",   // 290
                "Hensall",  // 291
                "HighLevel",  // 292
                "HighPrairie",  // 293
                "HighRiver",   // 294
                "Highlands",   // 295
                "HighlandsEast",   // 296
                "Hilliers",   // 297
                "Hillsborough",   // 298
                "Hillsburgh",   // 299
                "Hindonhill",   // 300
                "Hinton",   // 301
                "Holyrood",    // 302
                "Hope",    // 303
                "Horton",   // 304
                "Houston",    // 305
                "Howick",    // 306
                "HudsonBay",    // 307
                "Humbermouth",    // 308
                "Humboldt",    // 309
                "Huntsville",    // 310
                "Kaleden",    // 311
                "Kamloops",    // 312
                "Kamsack",    // 313
                "Kapuskasing",     // 314
                "Kedgwick",     // 315
                "Kelowna",     // 316
                "Kelsey",     // 317
                "Kenora",     // 318
                "Kensington",     // 319
                "Kent",     // 320
                "Kentville",     // 321
                "Keremeos",      // 322
                "Kerrobert",      // 323
                "Kimberley",      // 324
                "Kincardine",      // 325
                "Kindersley",     // 326
                "King",      // 327
                "Kingsclear",      // 328
                "Kingston",      // 329
                "Kingsville",       // 330
                "Kipling",       // 331
                "Kippens",       // 332
                "Kirkland",       // 333
                "Kitchener",       // 334
                "Kitimat",       // 335
                "Komoka",       // 336
                "Ladysmith",       // 337
                "Laird",       // 338
                "LakeCountry",       // 339
                "LakeCowichan",       // 340
                "LakeofBays",      // 341
                "Lakeshore",      // 342
                "Lakeview",      // 343
                "LambtonShores",      // 344
                "Lameque",      // 345
                "Lamont",      // 346
                "Landmark",      // 347
                "Langenburg",      // 348
                "Langham",      // 349
                "Langley",      // 350
                "Lanigan",     // 351
                "Lantz",     // 352
                "Lappe",     // 353
                "LaSalle",     // 354
                "Leamington",     // 355
                "Leduc",     // 356
                "Legal",     // 357
                "Lethbridge",     // 358
                "Lewisporte",     // 359
                "Lillooet",     // 360
                "Lincoln",     // 361
                "LionsBay",     // 362
                "Lloydminster",     // 363
                "LoganLake",     // 364
                "London",     // 365
                "Lorette",     // 366
                "Lorne",     // 367
                "Louise",     // 368
                "Loyalist",     // 369
                "Lumby",     // 370
                "Lumsden",     // 371
                "Lunenburg",   // 372
                "Macdonald",   // 373
                "Mackenzie",   // 374
                "Macklin",   // 375
                "Madoc",   // 376
                "Magrath",   // 377
                "MahoneBay",   // 378
                "Maidstone",   // 379
                "Malahide",   // 380
                "Malpeque",    // 381
                "MannersSutton",    // 382
                "Mapleton",    // 383
                "Marathon",    // 384
                "Markham",    // 385
                "Markstay",     // 386
                "Martensville",     // 387
                "Marystown",     // 388
                "Mattawa",     // 389
                "Maugerville",    // 390
                "Mayerthorpe",    // 391
                "McCreary",     // 392
                "McDougall",    // 393
                "McKellar",    // 394
                "Meaford",     // 395
                "Melancthon",     // 396
                "Melfort",      // 397
                "Melita",     // 398
                "Melville",     // 399
                "Memramcook",      // 400
                "Merrickville", // 401
                "Merritt", // 402
                "Midland", // 403
                "Millet", // 404
                "Milton", // 405
                "Minnedosa",// 406
                "Minto", // 407
                "Miramichi", // 408
                "Mission",  // 409
                "Mississauga",  // 410
                "Mitchell",  // 411
                "Moncton",  // 412
                "Mono",  // 413
                "Montague",  // 414
                "Moonbeam",  // 415
                "Morden",  // 416
                "Morinville",  // 417
                "Morris",  // 418
                "Mulmur",  // 419
                "Musquash",  // 420
                "Nain",  // 421
                "Nakusp",  // 422
                "Nanaimo",  // 423
                "Nanton",  // 424
                "Naramata",  // 425
                "Neepawa",  // 426
                "Neguac",  // 427
                "Nelson",  // 428
                "NewBandon",   // 429
                "NewGlasgow",  // 430
                "NewMaryland",  // 431
                "Newcastle",  // 432
                "Newmarket",  // 433
                "Nipawin",  // 434
                "Nipigon",  // 435
                "Nipissing",  // 436
                "Niverville",  // 437
                "Nobleford",  // 438
                "Noonan",  // 439
                "NorthBattleford",  // 440
                "NorthBay",  // 441
                "NorthVancouver",  // 442
                "Northampton",  // 443
                "Northesk",  // 444
                "Norton",  // 445
                "Norwich",  // 446
                "OakBay",  // 447
                "Oakland",  // 448
                "Oakview",  // 449
                "Oakville",  // 450
                "Okotoks",  // 451
                "Olds",  // 452
                "Oliver",  // 453
                "Orangeville",  // 454
                "Orillia",  // 455
                "Oromocto",  // 456
                "Oshawa",  // 457
                "Osler",  // 458
                "Osoyoos",  // 459
                "Ottawa",  // 460
                "Outlook",  // 461
                "Oxbow",  // 462
                "Oxford",  // 463
                "Oyen",  // 464
                "Paquetville",  // 465
                "Paradise",  // 466
                "Parksville",  // 467
                "Parrsboro",  // 468
                "Pasadena",  // 469
                "PeaceRiver",  // 470
                "Peachland",  // 471
                "Peel",  // 472
                "Pelham",  // 473
                "PelicanNarrows",  // 474
                "Pemberton",  // 475
                "Pembina",  // 476
                "Pembroke",  // 477
                "Penticton",  // 478
                "Perry",  // 479
                "Perth",  // 480
                "Peterborough",  // 481
                "PetitRocher",  // 482
                "Petitcodiac",  // 483
                "Petrolia",  // 484
                "Pickering",  // 485
                "Pictou",  // 486
                "Pinawa",  // 487
                "Pinehouse",  // 488
                "Piney",  // 489
                "Pipestone",  // 490
                "Placentia",  // 491
                "Plantagenet",  // 492
                "PlasterRock",// 493
                "Ponoka",// 494
                "Popkum",// 495
                "PortHope", // 496
                "PortMcNeill", // 497
                "PortMoody",// 498
                "PouchCove", // 499
                "Powassan",// 500
                "PrairieLakes",// 501
                "PrairieView", // 502
                "Preeceville", // 503
                "Prescott",// 504
                "Prince", // 505
                "PrinceAlbert", // 506
                "PrinceGeorge",  // 507
                "Princeton",  // 508
                "Provost", // 509
                "Puslinch", // 510
                "Rama", // 511
                "Ramara",  // 512
                "Raymond",  // 513
                "RedDeer",  // 514
                "RedLake",  // 515
                "Redcliff",  // 516
                "Redwater",  // 517
                "Regina",  // 518
                "ReginaBeach",  // 519
                "Reinland",  // 520
                "Renfrew",  // 521
                "RepulseBay",   // 522
                "Reynolds",   // 523
                "Richibucto",   // 524
                "Richmond",   // 525
                "Rimbey",   // 526
                "Ritchot",    // 527
                "Riverdale",    // 528
                "Riverview",     // 529
                "Rockwood",    // 530
                "Rogersville",    // 531
                "Roland",    // 532
                "Rosedale",    // 533
                "Rosser",    // 534
                "Rothesay",    // 535
                "Royston",    // 536
                "Russell"    // 537
        };
        return cities;
    }

    public static String[] GetFilteredList(String title) {
        String[] alphabets = new String[100];
        String[] cities = Cities.GetListData();
        int count = 0;
        for (int i = 0; i < cities.length; i++) {
            String firstChar = cities[i].charAt(0) + "";
            if (firstChar.equals(title)) {
                alphabets[count] = cities[i];
                count++;
            }
        }
        if (count > 0) {
            String[] finalList = new String[count - 1];
            for (int i = 0; i < count - 1; i++) {
                finalList[i] = alphabets[i];
            }
        return finalList;
        }
        return null;
    }

    public static ArrayAdapter<String> GetListAdaptor(Context context, String title) {
        ArrayAdapter<String> citiesyAdaptor = new ArrayAdapter<>(context, R.layout.inner_list_item);
        String[] cities = Cities.GetFilteredList(title);
        if (cities != null) {
            for (int i = 0; i < cities.length - 1; i++) {
                citiesyAdaptor.add(String.format(cities[i]));
            }
            return citiesyAdaptor;
        }
        return null;
    }

    public static int GetListOptionPos(String listOption) {
        String[] cities = Cities.GetListData();
        if (cities != null) {
            for (int i = 0; i < cities.length - 1; i++) {
               if(cities[i].equals(listOption))
                   return (i+1);
            }
        }
        return 0;
    }

    public static ArrayAdapter<String> GetFullListAdaptor(Context context) {
        ArrayAdapter<String> citiesyAdaptor = new ArrayAdapter<>(context, R.layout.inner_list_item);
        String[] cities = GetListData();
        for (int i = 0; i < cities.length - 1; i++) {
            citiesyAdaptor.add(String.format(cities[i]));
        }
        return citiesyAdaptor;
    }
}
