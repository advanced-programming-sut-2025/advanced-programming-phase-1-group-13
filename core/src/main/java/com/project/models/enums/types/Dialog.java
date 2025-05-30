package com.project.models.enums.types;

import com.project.models.enums.environment.Season;
import com.project.models.enums.environment.Weather;

public enum Dialog {
    DIALOG_1(NPCType.CLINT, "Morning. If you need ores smelted, just leave 'em with me. Got plenty of work to do.", 8, Season.SPRING, Weather.SUNNY, 0),
    DIALOG_2(NPCType.CLINT, "Rain's good for the crops, I guess. Not much use to a blacksmith, though.", 14, Season.SPRING, Weather.RAINY, 1),
    DIALOG_3(NPCType.CLINT, "Summer's rough... heat makes the forge unbearable. If you see me drinking a cold one, don’t judge.", 16, Season.SUMMER, Weather.SUNNY, 2),
    DIALOG_4(NPCType.CLINT, "Storms make me nervous. You know how dangerous lightning is near metal?", 10, Season.SUMMER, Weather.STORM, 1),
    DIALOG_5(NPCType.CLINT, "Fall’s my favorite season. Not too hot, not too cold. Just right for working metal.", 12, Season.FALL, Weather.SUNNY, 3),
    DIALOG_6(NPCType.CLINT, "Snow makes everything quiet... peaceful, I guess. Good time to focus on the craft.", 18, Season.WINTER, Weather.SNOW, 2),
    DIALOG_7(NPCType.CLINT, "I appreciate you stopping by... most folks just come here when they need something. Not much for small talk.", 17, Season.FALL, Weather.SUNNY, 3),
    DIALOG_8(NPCType.CLINT, "The forge never rests... even on the coldest winter days, there's always metal to shape.", 9, Season.WINTER, Weather.SNOW, 1),
    DIALOG_9(NPCType.CLINT, "The forge's always burning, even when it's freezing outside. Gotta keep the fire going, you know?", 11, Season.WINTER, Weather.SUNNY, 2),
    DIALOG_10(NPCType.CLINT, "Spring’s nice. Everything starts fresh... though I guess that doesn’t mean much when you spend all day indoors.", 13, Season.SPRING, Weather.SUNNY, 1),
    DIALOG_11(NPCType.CLINT, "Lightning makes me uneasy. Too many stories of sparks setting workshops on fire...", 15, Season.SUMMER, Weather.STORM, 2),
    DIALOG_12(NPCType.CLINT, "Rain makes the metal feel damp. Always double-check your tools on a day like this.", 9, Season.FALL, Weather.RAINY, 0),
    DIALOG_13(NPCType.CLINT, "People say I should get out more. But honestly? I just like my work.", 18, Season.FALL, Weather.SUNNY, 3),
    DIALOG_14(NPCType.CLINT, "The smell of hot metal and coal... that’s what I’ve known my whole life.", 8, Season.WINTER, Weather.SUNNY, 2),
    DIALOG_15(NPCType.CLINT, "Summer heat makes the workshop unbearable. If I look miserable, it’s ‘cause I am.", 16, Season.SUMMER, Weather.SUNNY, 1),
    DIALOG_16(NPCType.CLINT, "Some folks think working with metal is boring. Guess they don’t see the art in it.", 17, Season.SPRING, Weather.SUNNY, 3),
    DIALOG_17(NPCType.CLINT, "Snow makes deliveries a pain. If I seem grumpy, blame the icy roads.", 10, Season.WINTER, Weather.SNOW, 0),
    DIALOG_18(NPCType.CLINT, "You know... I appreciate you stopping by. Even if you don’t need anything smelted.", 19, Season.FALL, Weather.SUNNY, 3),
    DIALOG_19(NPCType.CLINT, "You ever just watch the embers glow in a forge? Sometimes it’s the little things that remind me why I do this.", 20, Season.WINTER, Weather.SUNNY, 2),
    DIALOG_20(NPCType.CLINT, "Folks don’t always appreciate the craft of blacksmithing... but without it, tools break, machines fail. I like knowing my work matters.", 14, Season.SPRING, Weather.RAINY, 3),

    DIALOG_21(NPCType.MORRIS, "Welcome to JojaMart, where convenience meets quality. Can I interest you in a special discount today?", 9, Season.SPRING, Weather.SUNNY, 0),
    DIALOG_22(NPCType.MORRIS, "Rainy days are perfect for shopping, wouldn’t you agree? Why struggle with farm work when JojaMart has everything ready for you?", 14, Season.SPRING, Weather.RAINY, 1),
    DIALOG_23(NPCType.MORRIS, "Summer heat can be relentless. Stay cool and stocked with JojaMart's premium selection of chilled beverages!", 11, Season.SUMMER, Weather.SUNNY, 2),
    DIALOG_24(NPCType.MORRIS, "Lightning storms remind us of nature’s unpredictability. But here at JojaMart, you’ll always find stability in our prices!", 16, Season.SUMMER, Weather.STORM, 0),
    DIALOG_25(NPCType.MORRIS, "The crisp air of fall makes everything feel fresh... just like our seasonal sales! Why not take advantage?", 12, Season.FALL, Weather.SUNNY, 3),
    DIALOG_26(NPCType.MORRIS, "Snow-covered streets can be inconvenient... unless you shop at JojaMart, where comfort and efficiency come first!", 18, Season.WINTER, Weather.SNOW, 2),
    DIALOG_27(NPCType.MORRIS, "I see you’re becoming a regular customer. Smart choice! Loyalty to Joja always brings its rewards.", 17, Season.FALL, Weather.SUNNY, 3),
    DIALOG_28(NPCType.MORRIS, "The holidays are approaching! Why wait for the madness when JojaMart offers unbeatable deals year-round?", 10, Season.WINTER, Weather.SNOW, 1),
    DIALOG_29(NPCType.MORRIS, "You know, people misunderstand JojaMart. We’re here to provide the best service possible, at a price no competitor can match.", 13, Season.SPRING, Weather.SUNNY, 2),
    DIALOG_30(NPCType.MORRIS, "Quality, affordability, efficiency—those are the pillars of JojaMart. I like to think our customers appreciate that.", 20, Season.SUMMER, Weather.SUNNY, 3),
    DIALOG_31(NPCType.MORRIS, "A storm might be raging outside, but here in JojaMart, the deals stay steady. Why not take shelter and browse our latest offers?", 15, Season.SUMMER, Weather.STORM, 1),
    DIALOG_32(NPCType.MORRIS, "Farm life must be exhausting. Wouldn’t it be easier to rely on Joja for all your needs instead?", 9, Season.SPRING, Weather.SUNNY, 2),
    DIALOG_33(NPCType.MORRIS, "Ah, fall—the season of change. Speaking of change, ever considered a Joja Membership? It could transform your life.", 17, Season.FALL, Weather.SUNNY, 3),
    DIALOG_34(NPCType.MORRIS, "Snowy days remind us why efficiency matters. JojaMart ensures you spend less time struggling and more time enjoying.", 14, Season.WINTER, Weather.SNOW, 2),
    DIALOG_35(NPCType.MORRIS, "People talk about ‘tradition’ like it’s always a good thing. Progress is better, don’t you think?", 10, Season.SPRING, Weather.RAINY, 0),
    DIALOG_36(NPCType.MORRIS, "Some say JojaMart lacks ‘personal charm.’ But I say consistency and low prices speak for themselves.", 16, Season.SUMMER, Weather.SUNNY, 2),
    DIALOG_37(NPCType.MORRIS, "In unpredictable weather, you want reliable service. Luckily, JojaMart doesn’t close for rain, snow, or storms.", 19, Season.WINTER, Weather.SNOW, 1),
    DIALOG_38(NPCType.MORRIS, "I take pride in knowing JojaMart provides stability. No surprises—just dependable prices and service.", 12, Season.FALL, Weather.SUNNY, 3),
    DIALOG_39(NPCType.MORRIS, "I see you’re becoming quite the regular. Smart move. Good customers recognize value when they see it.", 11, Season.SUMMER, Weather.SUNNY, 3),
    DIALOG_40(NPCType.MORRIS, "Success isn’t just about hard work. It’s about making smart choices... like shopping at JojaMart.", 20, Season.FALL, Weather.SUNNY, 2),

    DIALOG_41(NPCType.PIERRE, "Good morning! Need some fresh produce? I guarantee it's better than anything you'd find at JojaMart.", 8, Season.SPRING, Weather.SUNNY, 1),
    DIALOG_42(NPCType.PIERRE, "Rain’s good for the crops, but a smart farmer always plans ahead. Stock up while you can!", 14, Season.SPRING, Weather.RAINY, 1),
    DIALOG_43(NPCType.PIERRE, "Nothing like a hot summer day to remind you to grab some cold lemonade from the shop!", 11, Season.SUMMER, Weather.SUNNY, 2),
    DIALOG_44(NPCType.PIERRE, "Storms are nasty business... reminds me of how JojaMart tries to take over the town.", 16, Season.SUMMER, Weather.STORM, 0),
    DIALOG_45(NPCType.PIERRE, "Fall is my favorite time of year. The harvest is in full swing, and business is booming!", 12, Season.FALL, Weather.SUNNY, 3),
    DIALOG_46(NPCType.PIERRE, "Snow makes deliveries tricky, but don’t worry—I’ve stocked up on everything you need!", 18, Season.WINTER, Weather.SNOW, 2),
    DIALOG_47(NPCType.PIERRE, "You’re one of my best customers. I appreciate folks who support local business!", 17, Season.FALL, Weather.SUNNY, 3),
    DIALOG_48(NPCType.PIERRE, "Winter is a slow season for farming, but luckily, my store stays open no matter the weather!", 10, Season.WINTER, Weather.SNOW, 1),
    DIALOG_49(NPCType.PIERRE, "JojaMart is selling produce for cheap again. They’re ruining local farmers! Stick with me, and I’ll give you fair prices.", 13, Season.SPRING, Weather.SUNNY, 2),
    DIALOG_50(NPCType.PIERRE, "If you want quality, you shop at the General Store. It’s as simple as that!", 20, Season.SUMMER, Weather.SUNNY, 3),
    DIALOG_51(NPCType.PIERRE, "I take pride in knowing I’m supporting the community. Every sale keeps this town thriving!", 15, Season.FALL, Weather.SUNNY, 0),
    DIALOG_52(NPCType.PIERRE, "Local farmers work hard to grow the best crops. I wouldn’t sell anything I wouldn’t eat myself.", 9, Season.SPRING, Weather.SUNNY, 2),
    DIALOG_53(NPCType.PIERRE, "People talk about convenience like it’s everything. But tell me, does JojaMart have heart? No. That’s why I fight for my store.", 17, Season.FALL, Weather.SUNNY, 3),
    DIALOG_54(NPCType.PIERRE, "Winter’s tough, but my shop’s still stocked. No need to go hungry when I’ve got fresh goods!", 14, Season.WINTER, Weather.SNOW, 2),
    DIALOG_55(NPCType.PIERRE, "JojaMart thinks they can push me out. Hah! Not a chance. I’m not going anywhere!", 10, Season.SPRING, Weather.RAINY, 1),
    DIALOG_56(NPCType.PIERRE, "Sun’s high, crops are growing—it’s a great time for business!", 16, Season.SUMMER, Weather.SUNNY, 2),
    DIALOG_57(NPCType.PIERRE, "I might get competitive sometimes, but only because I care about this town!", 19, Season.WINTER, Weather.SNOW, 0),
    DIALOG_58(NPCType.PIERRE, "You’re a smart shopper, and I appreciate that. You won’t find better deals anywhere else!", 12, Season.FALL, Weather.SUNNY, 3),
    DIALOG_59(NPCType.PIERRE, "If you ever need advice on farming or supplies, I’m always happy to help!", 11, Season.SUMMER, Weather.SUNNY, 3),
    DIALOG_60(NPCType.PIERRE, "Running a shop isn’t just about profit... it’s about building something that lasts.", 20, Season.FALL, Weather.SUNNY, 2),

    DIALOG_61(NPCType.ROBIN, "Morning! Need any upgrades for your farm? A little investment can go a long way!", 8, Season.SPRING, Weather.SUNNY, 1),
    DIALOG_62(NPCType.ROBIN, "Rain’s great for trees! I just hope it doesn’t slow down my building projects.", 14, Season.SPRING, Weather.RAINY, 0),
    DIALOG_63(NPCType.ROBIN, "Summer is perfect for outdoor construction. Sunshine, fresh air, and plenty of lumber!", 11, Season.SUMMER, Weather.SUNNY, 2),
    DIALOG_64(NPCType.ROBIN, "Storms can be rough on old buildings. If your barn’s looking a little shaky, I can fix it up for you.", 16, Season.SUMMER, Weather.STORM, 1),
    DIALOG_65(NPCType.ROBIN, "Fall always makes me think about cozy cabins. A home should feel warm and inviting, don’t you think?", 12, Season.FALL, Weather.SUNNY, 3),
    DIALOG_66(NPCType.ROBIN, "Snow can be a hassle, but honestly? I love how peaceful everything looks when it’s covered in white.", 18, Season.WINTER, Weather.SNOW, 2),
    DIALOG_67(NPCType.ROBIN, "You seem to be settling into farm life well! If you ever need extra storage space, just let me know.", 17, Season.FALL, Weather.SUNNY, 3),
    DIALOG_68(NPCType.ROBIN, "Winter makes outdoor projects tricky, but I still keep busy designing new home additions!", 10, Season.WINTER, Weather.SNOW, 1),
    DIALOG_69(NPCType.ROBIN, "I love seeing people take pride in their homes. Nothing feels better than improving the space you live in!", 13, Season.SPRING, Weather.SUNNY, 2),
    DIALOG_70(NPCType.ROBIN, "A well-built farmhouse makes all the difference. Sturdy materials, smart design... I could talk about this all day!", 20, Season.SUMMER, Weather.SUNNY, 3),
    DIALOG_71(NPCType.ROBIN, "You’d be surprised how much personality a house can have. Little details really make a place feel special.", 15, Season.FALL, Weather.SUNNY, 1),
    DIALOG_72(NPCType.ROBIN, "Lumber prices are going up again... maybe I should start growing my own trees!", 9, Season.SPRING, Weather.SUNNY, 2),
    DIALOG_73(NPCType.ROBIN, "It’s nice having a job where I can create something lasting. A well-built home is a legacy in itself.", 17, Season.FALL, Weather.SUNNY, 3),
    DIALOG_74(NPCType.ROBIN, "I may work with wood all day, but even I appreciate the beauty of freshly fallen snow!", 14, Season.WINTER, Weather.SNOW, 2),
    DIALOG_75(NPCType.ROBIN, "Winter is perfect for planning new projects! Got any ideas for farm upgrades?", 10, Season.SPRING, Weather.RAINY, 0),
    DIALOG_76(NPCType.ROBIN, "Summer is my busiest season—everyone wants new barns, coops, and expansions!", 16, Season.SUMMER, Weather.SUNNY, 2),
    DIALOG_77(NPCType.ROBIN, "Building a house is one thing, but turning it into a home? That’s the real magic.", 19, Season.WINTER, Weather.SNOW, 1),
    DIALOG_78(NPCType.ROBIN, "Hard work pays off. Just look at how much your farm has grown!", 12, Season.FALL, Weather.SUNNY, 3),
    DIALOG_79(NPCType.ROBIN, "If you ever want advice on home improvements, I’ve got plenty of ideas!", 11, Season.SUMMER, Weather.SUNNY, 3),
    DIALOG_80(NPCType.ROBIN, "Designing, building, renovating... I love every part of the process!", 20, Season.FALL, Weather.SUNNY, 2),

    DIALOG_81(NPCType.WILLY, "Mornin’. The fish are bitin’ early, so best get yer line in the water soon!", 6, Season.SPRING, Weather.SUNNY, 1),
    DIALOG_82(NPCType.WILLY, "A rainy day like this? Perfect fer fishin’. The fish don’t mind a little water, do they?", 14, Season.SPRING, Weather.RAINY, 1),
    DIALOG_83(NPCType.WILLY, "A hot summer day makes the ocean call even louder. Cool breeze, good catch—what more do ya need?", 11, Season.SUMMER, Weather.SUNNY, 2),
    DIALOG_84(NPCType.WILLY, "Storms are tricky. The sea’s got a temper, and ya gotta respect it.", 16, Season.SUMMER, Weather.STORM, 0),
    DIALOG_85(NPCType.WILLY, "Fall’s when the fish start actin’ different. A smart fisherman adjusts his bait.", 12, Season.FALL, Weather.SUNNY, 3),
    DIALOG_86(NPCType.WILLY, "Snow on the docks feels strange... but the ocean keeps rollin’, just the same.", 18, Season.WINTER, Weather.SNOW, 2),
    DIALOG_87(NPCType.WILLY, "Yer getting better at fishin’. I can tell. Keep at it, and ya might be a legend someday.", 17, Season.FALL, Weather.SUNNY, 3),
    DIALOG_88(NPCType.WILLY, "Winter fishin’ ain’t easy, but there’s some rare catches out there if ya know where to look.", 10, Season.WINTER, Weather.SNOW, 1),
    DIALOG_89(NPCType.WILLY, "A man’s gotta earn his keep. Nothin’ wrong with honest, hard work.", 13, Season.SPRING, Weather.SUNNY, 2),
    DIALOG_90(NPCType.WILLY, "The sea teaches patience. Some folks don’t have it, but it’s the key to a good catch.", 20, Season.SUMMER, Weather.SUNNY, 3),
    DIALOG_91(NPCType.WILLY, "Fishin’ ain’t just about the catch. It’s about the peace, the rhythm, the life of the ocean.", 15, Season.FALL, Weather.SUNNY, 0),
    DIALOG_92(NPCType.WILLY, "The ocean never rests, and neither do fishermen.", 9, Season.SPRING, Weather.SUNNY, 2),
    DIALOG_93(NPCType.WILLY, "Some days are slow, some days are full of surprises. That’s just the way of the sea.", 17, Season.FALL, Weather.SUNNY, 3),
    DIALOG_94(NPCType.WILLY, "Cold weather don’t bother me much. I’d rather be out on the water, any day.", 14, Season.WINTER, Weather.SNOW, 2),
    DIALOG_95(NPCType.WILLY, "The sea’s got her moods. A fisherman’s gotta know how to read ‘em.", 10, Season.SPRING, Weather.RAINY, 0),
    DIALOG_96(NPCType.WILLY, "The ocean’s full of mysteries. I’ve spent my whole life tryin’ to learn ‘em.", 16, Season.SUMMER, Weather.SUNNY, 2),
    DIALOG_97(NPCType.WILLY, "Fishin’ in winter takes skill. But I reckon you’re learnin’ fast.", 19, Season.WINTER, Weather.SNOW, 1),
    DIALOG_98(NPCType.WILLY, "Yer a good sort. I can tell. A good fisherman, and an even better neighbor.", 12, Season.FALL, Weather.SUNNY, 3),
    DIALOG_99(NPCType.WILLY, "If ya ever want to learn a new trick, just stop by the shop. I’ve got plenty to share.", 11, Season.SUMMER, Weather.SUNNY, 3),
    DIALOG_100(NPCType.WILLY, "A simple life’s a good life. Fishin’, good company, and a warm meal at the end of the day.", 20, Season.FALL, Weather.SUNNY, 2),

    DIALOG_101(NPCType.MARNIE, "Good morning! The animals are happy today. Have you checked on yours yet?", 8, Season.SPRING, Weather.SUNNY, 1),
    DIALOG_102(NPCType.MARNIE, "Rain’s good for the grass, and happy cows love fresh greens!", 14, Season.SPRING, Weather.RAINY, 1),
    DIALOG_103(NPCType.MARNIE, "Summer’s here! Just be sure to keep plenty of water out for your animals in the heat.", 11, Season.SUMMER, Weather.SUNNY, 2),
    DIALOG_104(NPCType.MARNIE, "Storms can make the animals nervous. Be sure to check on them today!", 16, Season.SUMMER, Weather.STORM, 1),
    DIALOG_105(NPCType.MARNIE, "Fall is perfect for raising livestock—cool weather, happy animals, and plenty of food!", 12, Season.FALL, Weather.SUNNY, 3),
    DIALOG_106(NPCType.MARNIE, "Snow looks beautiful on the ranch, but keeping the animals warm takes extra work.", 18, Season.WINTER, Weather.SNOW, 2),
    DIALOG_107(NPCType.MARNIE, "You’re really taking good care of your farm animals! I can tell they love you.", 17, Season.FALL, Weather.SUNNY, 3),
    DIALOG_108(NPCType.MARNIE, "Winter can be tough on farm animals. Make sure they’ve got enough hay!", 10, Season.WINTER, Weather.SNOW, 0),
    DIALOG_109(NPCType.MARNIE, "Nothing’s better than seeing happy animals roaming the fields. Farm life really is special!", 13, Season.SPRING, Weather.SUNNY, 2),
    DIALOG_110(NPCType.MARNIE, "A good rancher knows that caring for animals isn’t just work—it’s a way of life.", 20, Season.SUMMER, Weather.SUNNY, 3),
    DIALOG_111(NPCType.MARNIE, "Every season brings its own joys. Right now, I’m just grateful for the fresh air and good company!", 15, Season.FALL, Weather.SUNNY, 0),
    DIALOG_112(NPCType.MARNIE, "It’s easy to love what you do when you’re surrounded by animals all day!", 9, Season.SPRING, Weather.SUNNY, 2),
    DIALOG_113(NPCType.MARNIE, "Ranching is hard work, but it’s also rewarding. You learn to appreciate the simple things.", 17, Season.FALL, Weather.SUNNY, 3),
    DIALOG_114(NPCType.MARNIE, "Winter mornings are chilly, but nothing warms the heart like seeing the animals safe and cozy.", 14, Season.WINTER, Weather.SNOW, 2),
    DIALOG_115(NPCType.MARNIE, "Rainy days can be peaceful. Just the sound of raindrops and happy animals munching on fresh food.", 10, Season.SPRING, Weather.RAINY, 1),
    DIALOG_116(NPCType.MARNIE, "Taking care of animals teaches patience. They trust you when you treat them right.", 16, Season.SUMMER, Weather.SUNNY, 2),
    DIALOG_117(NPCType.MARNIE, "Snow makes everything quiet and peaceful. The ranch feels different this time of year.", 19, Season.WINTER, Weather.SNOW, 1),
    DIALOG_118(NPCType.MARNIE, "You're doing a great job taking care of your animals! I’m always happy to help if you need advice.", 12, Season.FALL, Weather.SUNNY, 3),
    DIALOG_119(NPCType.MARNIE, "If you ever need supplies, just stop by the ranch. I’ve got plenty of food and treats for happy animals!", 11, Season.SUMMER, Weather.SUNNY, 3),
    DIALOG_120(NPCType.MARNIE, "Animals bring so much joy. Seeing them happy makes all the hard work worth it!", 20, Season.FALL, Weather.SUNNY, 2),

    DIALOG_121(NPCType.GUS, "Welcome to the Stardrop Saloon! Let me know if you need a hot meal or a refreshing drink.", 8, Season.SPRING, Weather.SUNNY, 1),
    DIALOG_122(NPCType.GUS, "Rainy days make me want to cook something warm and comforting. How about a fresh bowl of soup?", 14, Season.SPRING, Weather.RAINY, 0),
    DIALOG_123(NPCType.GUS, "Summer’s here! Nothing beats an ice-cold beverage after a long day of work.", 11, Season.SUMMER, Weather.SUNNY, 2),
    DIALOG_124(NPCType.GUS, "Storms make people stay indoors... good thing the saloon is the perfect place to wait it out.", 16, Season.SUMMER, Weather.STORM, 0),
    DIALOG_125(NPCType.GUS, "Fall is great for cooking! The harvest brings so many fresh ingredients.", 12, Season.FALL, Weather.SUNNY, 3),
    DIALOG_126(NPCType.GUS, "Snow makes everything feel cozy. How about a nice warm meal?", 18, Season.WINTER, Weather.SNOW, 2),
    DIALOG_127(NPCType.GUS, "You’re always welcome here! Good food, good drinks, and good company—that’s what it’s all about.", 17, Season.FALL, Weather.SUNNY, 3),
    DIALOG_128(NPCType.GUS, "Running the saloon keeps me busy, but I love making sure everyone feels at home.", 10, Season.WINTER, Weather.SNOW, 1),
    DIALOG_129(NPCType.GUS, "Cooking is my passion! If you ever need a good recipe, I’d be happy to share.", 13, Season.SPRING, Weather.SUNNY, 2),
    DIALOG_130(NPCType.GUS, "The saloon is open late, so stop by anytime. A good meal is never a bad idea.", 20, Season.SUMMER, Weather.SUNNY, 3),
    DIALOG_131(NPCType.GUS, "Fall always makes me nostalgic. There’s nothing like the smell of fresh-baked bread this time of year.", 15, Season.FALL, Weather.SUNNY, 1),
    DIALOG_132(NPCType.GUS, "Food brings people together. That’s why I love running the saloon!", 9, Season.SPRING, Weather.SUNNY, 2),
    DIALOG_133(NPCType.GUS, "Business has its ups and downs, but I wouldn’t trade this job for anything.", 17, Season.FALL, Weather.SUNNY, 3),
    DIALOG_134(NPCType.GUS, "Winter’s tough on business, but a good bowl of stew keeps folks coming in!", 14, Season.WINTER, Weather.SNOW, 2),
    DIALOG_135(NPCType.GUS, "A rainy day calls for something warm and filling. Maybe I’ll whip up my famous sauce!", 10, Season.SPRING, Weather.RAINY, 0),
    DIALOG_136(NPCType.GUS, "The summer rush keeps me busy, but I love serving up fresh meals for everyone.", 16, Season.SUMMER, Weather.SUNNY, 2),
    DIALOG_137(NPCType.GUS, "Cold nights call for hot food. There’s nothing better than a hearty meal after a long day.", 19, Season.WINTER, Weather.SNOW, 1),
    DIALOG_138(NPCType.GUS, "You’ve got good taste in food! Always happy to serve you something special.", 12, Season.FALL, Weather.SUNNY, 3),
    DIALOG_139(NPCType.GUS, "If you ever want to try cooking something new, I’ve got plenty of recipes!", 11, Season.SUMMER, Weather.SUNNY, 3),
    DIALOG_140(NPCType.GUS, "A good meal can make any day better. That’s why I do what I do!", 20, Season.FALL, Weather.SUNNY, 2),

    DIALOG_141(NPCType.SEBASTIAN, "Morning… I guess. I don’t really do mornings.", 10, Season.SPRING, Weather.SUNNY, 1),
    DIALOG_142(NPCType.SEBASTIAN, "Rain’s nice. No one expects you to go outside when it’s pouring.", 14, Season.SPRING, Weather.RAINY, 1),
    DIALOG_143(NPCType.SEBASTIAN, "Summer’s overrated. Too hot, too bright. I’ll stick to my laptop and an iced coffee.", 12, Season.SUMMER, Weather.SUNNY, 2),
    DIALOG_144(NPCType.SEBASTIAN, "Storm’s coming… cool. Maybe I’ll sit by the window and pretend I’m in a dramatic movie scene.", 16, Season.SUMMER, Weather.STORM, 0),
    DIALOG_145(NPCType.SEBASTIAN, "Fall is the best season. Cool air, cloudy skies, minimal social obligations.", 11, Season.FALL, Weather.SUNNY, 3),
    DIALOG_146(NPCType.SEBASTIAN, "Snow’s nice when you’re inside, warm, and don’t have to shovel it.", 18, Season.WINTER, Weather.SNOW, 2),
    DIALOG_147(NPCType.SEBASTIAN, "You actually came to talk to me? Guess I should be flattered.", 17, Season.FALL, Weather.SUNNY, 3),
    DIALOG_148(NPCType.SEBASTIAN, "Winter means even fewer people outside. Finally, some peace and quiet.", 10, Season.WINTER, Weather.SNOW, 1),
    DIALOG_149(NPCType.SEBASTIAN, "Everyone’s out planting stuff in the spring… I’ll be in my room, avoiding responsibilities.", 13, Season.SPRING, Weather.SUNNY, 2),
    DIALOG_150(NPCType.SEBASTIAN, "The sun is way too aggressive today. I might boycott it.", 20, Season.SUMMER, Weather.SUNNY, 3),
    DIALOG_151(NPCType.SEBASTIAN, "People say I should ‘get out more.’ I say the world should come to me instead.", 15, Season.FALL, Weather.SUNNY, 1),
    DIALOG_152(NPCType.SEBASTIAN, "Coding all night means I get to skip mornings entirely. Highly recommend.", 9, Season.SPRING, Weather.SUNNY, 2),
    DIALOG_153(NPCType.SEBASTIAN, "It’s weird how quiet the town feels in fall. Not complaining, though.", 17, Season.FALL, Weather.SUNNY, 3),
    DIALOG_154(NPCType.SEBASTIAN, "Snow makes everything still… kind of eerie. I like it.", 14, Season.WINTER, Weather.SNOW, 2),
    DIALOG_155(NPCType.SEBASTIAN, "Rainy days are perfect for staring at a screen and avoiding small talk.", 10, Season.SPRING, Weather.RAINY, 0),
    DIALOG_156(NPCType.SEBASTIAN, "I’m supposed to ‘enjoy summer.’ Yeah, I’ll get right on that.", 16, Season.SUMMER, Weather.SUNNY, 2),
    DIALOG_157(NPCType.SEBASTIAN, "Snow’s falling… Guess I should grab my jacket. Or just stay inside.", 19, Season.WINTER, Weather.SNOW, 0),
    DIALOG_158(NPCType.SEBASTIAN, "You actually stopped by. Huh. I guess I don’t hate that.", 12, Season.FALL, Weather.SUNNY, 3),
    DIALOG_159(NPCType.SEBASTIAN, "If you need me, I’ll be pretending to work while listening to loud music.", 11, Season.SUMMER, Weather.SUNNY, 3),
    DIALOG_160(NPCType.SEBASTIAN, "Life’s weird. But at least I have my motorcycle.", 20, Season.FALL, Weather.SUNNY, 2),

    DIALOG_161(NPCType.ABIGAIL, "Morning! Ugh, why does the sun have to be so… bright?", 10, Season.SPRING, Weather.SUNNY, 1),
    DIALOG_162(NPCType.ABIGAIL, "Rainy days are the best. Perfect for curling up with a spooky book.", 14, Season.SPRING, Weather.RAINY, 0),
    DIALOG_163(NPCType.ABIGAIL, "Summer's nice, I guess… but I’d rather be exploring caves than sitting in the sun.", 12, Season.SUMMER, Weather.SUNNY, 2),
    DIALOG_164(NPCType.ABIGAIL, "Storms are awesome! The thunder makes everything feel more dramatic.", 16, Season.SUMMER, Weather.STORM, 1),
    DIALOG_165(NPCType.ABIGAIL, "Fall is the best season. Cool air, crunchy leaves, and everything feels a little more mysterious.", 11, Season.FALL, Weather.SUNNY, 3),
    DIALOG_166(NPCType.ABIGAIL, "Snow makes the whole town feel peaceful… almost too peaceful.", 18, Season.WINTER, Weather.SNOW, 2),
    DIALOG_167(NPCType.ABIGAIL, "You actually came to talk to me? Nice. Most people are too busy with their boring routines.", 17, Season.FALL, Weather.SUNNY, 3),
    DIALOG_168(NPCType.ABIGAIL, "Winter’s kind of magical, don’t you think? The snow makes everything feel unreal.", 10, Season.WINTER, Weather.SNOW, 1),
    DIALOG_169(NPCType.ABIGAIL, "Spring is nice, I guess, but I wish it came with more adventure.", 13, Season.SPRING, Weather.SUNNY, 2),
    DIALOG_170(NPCType.ABIGAIL, "Summer is way too cheerful. Where’s the mystery? The intrigue?", 20, Season.SUMMER, Weather.SUNNY, 3),
    DIALOG_171(NPCType.ABIGAIL, "People always expect me to act ‘normal.’ But normal is boring.", 15, Season.FALL, Weather.SUNNY, 1),
    DIALOG_172(NPCType.ABIGAIL, "Ever feel like you're meant for something more? Like you belong somewhere… bigger?", 9, Season.SPRING, Weather.SUNNY, 2),
    DIALOG_173(NPCType.ABIGAIL, "Fall feels like a fresh start. I always get the urge to go on an adventure this time of year.", 17, Season.FALL, Weather.SUNNY, 3),
    DIALOG_174(NPCType.ABIGAIL, "Snowfall makes the world feel… frozen in time. I kinda love it.", 14, Season.WINTER, Weather.SNOW, 2),
    DIALOG_175(NPCType.ABIGAIL, "A good storm is the perfect excuse to stay inside and play games all day.", 10, Season.SPRING, Weather.RAINY, 0),
    DIALOG_176(NPCType.ABIGAIL, "Bright, sunny day? Guess I should pretend to be normal and go outside.", 16, Season.SUMMER, Weather.SUNNY, 2),
    DIALOG_177(NPCType.ABIGAIL, "There’s something eerie about a quiet winter morning… I kind of love it.", 19, Season.WINTER, Weather.SNOW, 0),
    DIALOG_178(NPCType.ABIGAIL, "You actually get me. That’s… kinda rare.", 12, Season.FALL, Weather.SUNNY, 3),
    DIALOG_179(NPCType.ABIGAIL, "If I disappear for a few days, assume I’ve gone on an adventure.", 11, Season.SUMMER, Weather.SUNNY, 3),
    DIALOG_180(NPCType.ABIGAIL, "Some people just accept things the way they are. But me? I want something more.", 20, Season.FALL, Weather.SUNNY, 2),

    DIALOG_181(NPCType.HARVEY, "Good morning! Remember to take care of yourself—health is the foundation of everything.", 8, Season.SPRING, Weather.SUNNY, 1),
    DIALOG_182(NPCType.HARVEY, "Rainy days are peaceful, but don’t forget to stay warm and dry. It’s easy to catch a cold this time of year.", 14, Season.SPRING, Weather.RAINY, 1),
    DIALOG_183(NPCType.HARVEY, "Summer’s nice, but dehydration is a real concern. Drink plenty of water and take breaks from the heat!", 11, Season.SUMMER, Weather.SUNNY, 2),
    DIALOG_184(NPCType.HARVEY, "Storms can be dangerous. Stay indoors if you can, and don’t take unnecessary risks.", 16, Season.SUMMER, Weather.STORM, 0),
    DIALOG_185(NPCType.HARVEY, "Fall is my favorite season—cool air, fewer seasonal illnesses, and beautiful scenery.", 12, Season.FALL, Weather.SUNNY, 3),
    DIALOG_186(NPCType.HARVEY, "Cold weather can be tough on the body. Make sure to dress warmly and keep your immune system strong.", 18, Season.WINTER, Weather.SNOW, 2),
    DIALOG_187(NPCType.HARVEY, "You seem like someone who takes good care of yourself. That’s always good to see!", 17, Season.FALL, Weather.SUNNY, 3),
    DIALOG_188(NPCType.HARVEY, "Winter brings its own challenges—dry air, shorter days... It’s important to keep moving and stay healthy.", 10, Season.WINTER, Weather.SNOW, 1),
    DIALOG_189(NPCType.HARVEY, "Spring brings new beginnings. It’s a great time to establish healthy habits!", 13, Season.SPRING, Weather.SUNNY, 2),
    DIALOG_190(NPCType.HARVEY, "Long hours at the clinic can be tiring, but knowing I’m helping people makes it all worth it.", 20, Season.SUMMER, Weather.SUNNY, 3),
    DIALOG_191(NPCType.HARVEY, "A balanced diet, fresh air, and good sleep—that’s the recipe for a healthy life.", 15, Season.FALL, Weather.SUNNY, 1),
    DIALOG_192(NPCType.HARVEY, "Preventative care is just as important as treatment. Stay on top of your health, and your future self will thank you.", 9, Season.SPRING, Weather.SUNNY, 2),
    DIALOG_193(NPCType.HARVEY, "Fall is the perfect time for outdoor activities—just don’t overdo it!", 17, Season.FALL, Weather.SUNNY, 3),
    DIALOG_194(NPCType.HARVEY, "Winter can feel isolating, but staying connected with others is just as important as physical health.", 14, Season.WINTER, Weather.SNOW, 2),
    DIALOG_195(NPCType.HARVEY, "If you ever need a checkup, don’t hesitate to stop by the clinic. I’m always happy to help.", 10, Season.SPRING, Weather.RAINY, 0),
    DIALOG_196(NPCType.HARVEY, "Summer can be exhausting, even if you’re used to hard work. Listen to your body and rest when you need to.", 16, Season.SUMMER, Weather.SUNNY, 2),
    DIALOG_197(NPCType.HARVEY, "Cold weather means more cases of flu. If you’re feeling under the weather, don’t ignore it!", 19, Season.WINTER, Weather.SNOW, 1),
    DIALOG_198(NPCType.HARVEY, "Your health is your most valuable asset. Taking care of it should always be a priority.", 12, Season.FALL, Weather.SUNNY, 3),
    DIALOG_199(NPCType.HARVEY, "I may be busy with work, but I always have time to help those in need.", 11, Season.SUMMER, Weather.SUNNY, 3),
    DIALOG_200(NPCType.HARVEY, "Taking care of others is rewarding, but I have to remind myself to take care of my own health, too.", 20, Season.FALL, Weather.SUNNY, 2),

    DIALOG_201(NPCType.LEAH, "Good morning! The sunlight hits the leaves just right today. Nature is an artist too, you know.", 8, Season.SPRING, Weather.SUNNY, 1),
    DIALOG_202(NPCType.LEAH, "Rain makes the world feel alive. The plants, the soil… it’s like everything is drinking in the moment.", 14, Season.SPRING, Weather.RAINY, 1),
    DIALOG_203(NPCType.LEAH, "Summer’s heat can be exhausting, but the river is a perfect place to cool off and find inspiration.", 11, Season.SUMMER, Weather.SUNNY, 2),
    DIALOG_204(NPCType.LEAH, "Storms make everything feel dramatic. I love watching the sky shift—so much power in nature!", 16, Season.SUMMER, Weather.STORM, 1),
    DIALOG_205(NPCType.LEAH, "Fall is my favorite time of year. The colors, the crisp air—it’s like the world is painting itself.", 12, Season.FALL, Weather.SUNNY, 3),
    DIALOG_206(NPCType.LEAH, "Snow makes everything quiet and peaceful. It’s a great time to reflect and create.", 18, Season.WINTER, Weather.SNOW, 2),
    DIALOG_207(NPCType.LEAH, "You seem like the type who appreciates the little things. That’s important in life.", 17, Season.FALL, Weather.SUNNY, 3),
    DIALOG_208(NPCType.LEAH, "Winter can be harsh, but I love how the landscape transforms. It’s beautiful in a different way.", 10, Season.WINTER, Weather.SNOW, 0),
    DIALOG_209(NPCType.LEAH, "Spring brings new ideas. I always get inspired to start a new sculpture this time of year!", 13, Season.SPRING, Weather.SUNNY, 2),
    DIALOG_210(NPCType.LEAH, "A warm meal, good company, and time to create… what else could you ask for?", 20, Season.SUMMER, Weather.SUNNY, 3),
    DIALOG_211(NPCType.LEAH, "Every season has its own beauty. Fall just happens to be my favorite.", 15, Season.FALL, Weather.SUNNY, 1),
    DIALOG_212(NPCType.LEAH, "I love carving sculptures, but sometimes nature already creates the perfect masterpiece.", 9, Season.SPRING, Weather.SUNNY, 2),
    DIALOG_213(NPCType.LEAH, "I used to feel restless… but now I think I’ve found the place I’m supposed to be.", 17, Season.FALL, Weather.SUNNY, 3),
    DIALOG_214(NPCType.LEAH, "Snowy days are perfect for staying inside with a sketchbook and a cup of tea.", 14, Season.WINTER, Weather.SNOW, 2),
    DIALOG_215(NPCType.LEAH, "Rain makes everything feel soft and quiet. It’s kind of peaceful, don’t you think?", 10, Season.SPRING, Weather.RAINY, 0),
    DIALOG_216(NPCType.LEAH, "Summer makes me want to travel… find new places to explore, new things to create.", 16, Season.SUMMER, Weather.SUNNY, 2),
    DIALOG_217(NPCType.LEAH, "The snow blankets everything like a fresh canvas. I love that idea.", 19, Season.WINTER, Weather.SNOW, 1),
    DIALOG_218(NPCType.LEAH, "You have a creative soul, I can tell. That’s something special.", 12, Season.FALL, Weather.SUNNY, 3),
    DIALOG_219(NPCType.LEAH, "Art and nature… two things that always make life feel richer.", 11, Season.SUMMER, Weather.SUNNY, 3),
    DIALOG_220(NPCType.LEAH, "If you ever want to try sculpting, let me know! I’d love to show you a few techniques.", 20, Season.FALL, Weather.SUNNY, 2),

    ;

    private final NPCType speaker;
    private final String message;
    private final int timeOfDay;
    private final Season season;
    private final Weather weather;
    private final int friendshipLevel;

    Dialog(NPCType speaker, String message, int timeOfDay, Season season, Weather weather, int friendshipLevel) {
        this.speaker = speaker;
        this.message = message;
        this.timeOfDay = timeOfDay;
        this.season = season;
        this.weather = weather;
        this.friendshipLevel = friendshipLevel;
    }

    public NPCType getSpeaker() {
        return speaker;
    }

    public String getMessage() {
        return message;
    }

    public int getTimeOfDay() {
        return timeOfDay;
    }

    public Season getSeason() {
        return season;
    }

    public Weather getWeather() {
        return weather;
    }

    public int getFriendshipLevel() {
        return friendshipLevel;
    }

    public static Dialog getDialogBySituation(NPCType speaker, int timeOfDay, Season season,
                                              Weather weather, int friendshipLevel) {
        for (Dialog dialogType : Dialog.values()) {
            if (dialogType.getSpeaker().equals(speaker) &&
//                    dialogType.getTimeOfDay() == timeOfDay &&
                    dialogType.getSeason().equals(season) &&
                    dialogType.getWeather().equals(weather) &&
                    dialogType.getFriendshipLevel() == friendshipLevel) {
                return dialogType;
            }
        }
        return null;
    }
}
