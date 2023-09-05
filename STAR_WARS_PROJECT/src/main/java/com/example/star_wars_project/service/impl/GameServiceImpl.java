package com.example.star_wars_project.service.impl;

import com.example.star_wars_project.model.binding.GameAddBindingModel;
import com.example.star_wars_project.model.entity.Game;
import com.example.star_wars_project.model.entity.Picture;
import com.example.star_wars_project.model.entity.enums.PlatformNameEnum;
import com.example.star_wars_project.model.view.AllGamesViewModel;
import com.example.star_wars_project.repository.GameRepository;
import com.example.star_wars_project.repository.PictureRepository;
import com.example.star_wars_project.repository.PlatformRepository;
import com.example.star_wars_project.repository.UserRepository;
import com.example.star_wars_project.service.CloudinaryService;
import com.example.star_wars_project.service.GameService;
import com.example.star_wars_project.utils.CloudinaryImage;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GameServiceImpl implements GameService {
    private final GameRepository gameRepository;
    private final ModelMapper modelMapper;
    private final PictureRepository pictureRepository;
    private final CloudinaryService cloudinaryService;
    private final UserRepository userRepository;
    private final PlatformRepository platformRepository;

    public GameServiceImpl(GameRepository gameRepository, ModelMapper modelMapper, PictureRepository pictureRepository, CloudinaryService cloudinaryService, UserRepository userRepository, PlatformRepository platformRepository) {
        this.gameRepository = gameRepository;
        this.modelMapper = modelMapper;
        this.pictureRepository = pictureRepository;
        this.cloudinaryService = cloudinaryService;
        this.userRepository = userRepository;
        this.platformRepository = platformRepository;
    }

    @Override
    public List<AllGamesViewModel> findAllGamesOrderedByReleaseDate() {
        return gameRepository
                .findAllGamesByReleaseDate()
                .stream()
                .map(this::mapsGameToGameView)
                .collect(Collectors.toList());
    }

    @Override
    public List<AllGamesViewModel> findAllGamesWithValueNullOrFalse() {
        return gameRepository
                .findGamesThatAreNotApproved()
                .stream()
                .map(this::mapsGameToGameView)
                .collect(Collectors.toList());
    }

    private AllGamesViewModel mapsGameToGameView(Game game) {
        AllGamesViewModel currentGame = modelMapper.map(game, AllGamesViewModel.class);
        Picture pictureByGameId = pictureRepository.findPictureByGame_Id(game.getId());
        currentGame.setPicture(pictureByGameId);
        return currentGame;
    }

    @Override
    public Game findGame(Long id) {
        return gameRepository.findById(id).orElse(null);
    }

    @Override
    public void addGame(GameAddBindingModel gameAddBindingModel, String currentUserUsername) throws IOException {
        Game game = modelMapper.map(gameAddBindingModel, Game.class);
        game.setAuthor(userRepository.findUserByUsername(currentUserUsername).orElse(null));
        game.setPlatform(platformRepository.findPlatformByName(gameAddBindingModel.getPlatform()));
        gameRepository.save(game);

        MultipartFile pictureMultipartFile = gameAddBindingModel.getPicture();
        String pictureMultipartFileTitle = gameAddBindingModel.getPictureTitle();
        final CloudinaryImage uploaded = cloudinaryService.upload(pictureMultipartFile);

        Picture picture = new Picture();
        picture.setPictureUrl(uploaded.getUrl());
        picture.setPublicId(uploaded.getPublicId());

        picture.setTitle(pictureMultipartFileTitle);
        picture.setAuthor(userRepository.findUserByUsername(currentUserUsername).orElse(null));
        picture.setGame(gameRepository.findGameByTitle(gameAddBindingModel.getTitle()));
        pictureRepository.save(picture);
    }


    @Override
    public void approveGameWithId(Long id) {
        Game game = gameRepository.findGameById(id);
        game.setApproved(true);
        gameRepository.save(game);
    }

    @Override
    public void deleteGameWithId(Long id) {
        List<Picture> allByGameId = pictureRepository.findAllByGame_Id(id);
        pictureRepository.deleteAll(allByGameId);
        gameRepository.deleteById(id);
    }

    @Override
    public void initGames() {

        if (gameRepository.count() > 0) {
            return;
        }

        Game game1 = new Game();
        game1.setApproved(null);
        game1.setDescription("From Respawn Entertainment comes a brand-new action adventure game which tells an original Star Wars story about Cal Kestis, a Padawan who survived the events of Star Wars: Revenge of the Sith. Play, and become a Jedi.\n" +
                "\n" +
                "Key Features: \n" +
                "\n" +
                "Feel The Force - Master lightsaber combat forms to refine striking, parrying and dodging your enemies. Use your Jedi weapon and the Force to take on any challenge.\n" +
                "\n" +
                "A New Star Wars Story - As one of the last Jedi, you must do whatever it takes to survive. Complete your Jedi training before the Inquisitors discover your plan to rebuild the Jedi Order.\n" +
                "\n" +
                "The Galaxy Awaits - Explore ancient forests, windswept cliffs, and haunted jungles as you decide when and where you want to go next.\n" +
                "\n" +
                "Update - EA and Respawn Entertainment have released a free content update for Star Wars Jedi: Fallen Order, allowing players to revisit the story of Cal Kestis in New Journey+ which unlocks new cosmetics and new game modes such as Combat Challenges and the Battle Grid, which put your skills as a Jedi to the ultimate test. ");
        game1.setReleaseDate(LocalDate.of(2019, 11, 15));
        game1.setTitle("Star Wars Jedi: Fallen Order");
        game1.setAuthor(userRepository.findUserByUsername("Admin").orElse(null));
        game1.setPlatform(platformRepository.findPlatformByName(PlatformNameEnum.PC));
        game1.setVideoUrl("xIl2z5wwjdA");

        Picture picture1 = new Picture();
        picture1.setPictureUrl("https://res.cloudinary.com/dedh1hh8k/image/upload/v1680880164/Star_Wars_Jedi_Fallen_Order_bx8s4f.webp");
        picture1.setPublicId("Star_Wars_Jedi_Fallen_Order_bx8s4f");
        picture1.setTitle("Star Wars Jedi: Fallen Order");
        picture1.setAuthor(userRepository.findUserByUsername("Admin").orElse(null));
        picture1.setGame(game1);

        gameRepository.save(game1);
        pictureRepository.save(picture1);


        Game game2 = new Game();
        game2.setApproved(null);
        game2.setDescription("Embark on an all-new Battlefront experience from the bestselling Star Wars game franchise of all time. Become the hero and play as a fearless trooper, pilot a legendary starfighter, fight as your favorite iconic Star Wars character, or forge a new path as an elite special forces soldier through an emotionally gripping new Star Wars story.\n" +
                "\n" +
                "A New Hero, a Story Untold - Jump into the boots of an elite special forces soldier, equally lethal on the ground and space, in an emotionally gripping new Star Wars campaign that spans over 30 years and bridges events between the films’ Star Wars: Return of the Jedi and Star Wars: The Force Awakens.\n" +
                "\n" +
                "The Ultimate Star Wars Battleground - A Star Wars multiplayer universe unmatched in variety and breadth where up to 40 players fight as iconic heroes, authentic-to-era troopers and in a massive array of vehicles on land and in the air – as battle rages through the galaxy.\n" +
                "\n" +
                "Galactic-Scale Space Combat - Space combat has been designed for Star Wars Battlefront™ II from the ground up with distinct handling, weapons and customization options. Join your squadron and weave in between asteroids fields, fly through Imperial Dock Yards and take down massive capital ships as you pilot legendary starfighters in high stakes dogfights with up to 24 players and 40 AI ships.\n" +
                "\n" +
                "Better Together - Team up with a friend from the comfort of your couch with two-player offline split-screen play*. Earn rewards, customize your troopers and heroes, and bring your upgrades with you on the online multiplayer battleground.\n" +
                "\n" +
                "Master Your Hero - Not just an iconic hero- your hero. Master your craft with customizable character progression. Equip ability modifiers, unique to each hero, trooper class, and starfighter. Use these ability modifiers to adapt and modify your character’s core powers, either as lethal active effects on your opponents, helpful status boosts, or tactical assistance, to counter any opponent on the battlefront.\n" +
                "\n" +
                "*split-screen co-op only available on PlayStation 4 and Xbox One.");
        game2.setReleaseDate(LocalDate.of(2017, 11, 17));
        game2.setTitle("Star Wars Battlefront II");
        game2.setAuthor(userRepository.findUserByUsername("Admin").orElse(null));
        game2.setPlatform(platformRepository.findPlatformByName(PlatformNameEnum.CONSOLE));
        game2.setVideoUrl("_q51LZ2HpbE");

        Picture picture2 = new Picture();
        picture2.setPictureUrl("https://res.cloudinary.com/dedh1hh8k/image/upload/v1680880164/Star_Wars_Battlefront_II_i3pyfl.webp");
        picture2.setPublicId("Star_Wars_Battlefront_II_i3pyfl");
        picture2.setTitle("Star Wars Battlefront II");
        picture2.setAuthor(userRepository.findUserByUsername("Admin").orElse(null));
        picture2.setGame(game2);

        gameRepository.save(game2);
        pictureRepository.save(picture2);


        Game game3 = new Game();
        game3.setApproved(null);
        game3.setDescription("Master the art of starfighter combat in the authentic piloting experience Star Wars: Squadrons. Buckle up and feel the rush of first-person multiplayer space dogfights alongside your squadron. Pilots who enlist will step into the cockpits of legendary starfighters, from both the New Republic and Imperial fleets, and fight in strategic 5v5 space battles. Modify your starfighter and adjust the composition of your squadron to suit varying playstyles and crush the opposition. Pilots will triumph as a team and complete tactical objectives across known and never-before-seen battlefields, including the gas giant of Yavin Prime and the shattered moon of Galitan.\n" +
                "\n" +
                "Key Features\n" +
                "\n" +
                "    All Wings Report In – Plan skirmishes with your squadron in the briefing room before taking off to the evolving battlefields across the galaxy. Compete in intense 5v5 multiplayer dogfights or unite with your squadron to tip the scales in monumental fleet battles. Together, you’re the galaxy’s finest. \n" +
                "\n" +
                "    Master Legendary Starfighters – Take control of different classes of starfighters from both the New Republic and Imperial fleets – including the agile A-wing and the devastating TIE bomber. Modify your ship, divert the power between its systems, and destroy your opponents in strategic space dogfights. \n" +
                "\n" +
                "    Live Your Star Wars Pilot Fantasy – The cockpit is your home. Use its dashboards to your advantage and – with just a thin hull of metal and glass between you and the perils of space – feel the intensity of combat from a first-person perspective. Take off in thrilling multiplayer modes and a unique single-player Star Wars story, which covers a key campaign near the conclusion of the Galactic Civil War. Immerse yourself in the pilot’s seat completely with the option to play the entirety of Star Wars: Squadrons in VR. \n" +
                "\n" +
                "    The Mission is Clear – Star Wars: Squadrons is a fully self-contained experience from day one, where you earn rewards through play. Climb the ranks and unlock new components like weapons, hulls, engines, shields, and cosmetic items in a clear path for progression that keeps gameplay fresh and engaging. ");
        game3.setReleaseDate(LocalDate.of(2020, 10, 1));
        game3.setTitle("Star Wars: Squadrons");
        game3.setAuthor(userRepository.findUserByUsername("Admin").orElse(null));
        game3.setPlatform(platformRepository.findPlatformByName(PlatformNameEnum.PC));
        game3.setVideoUrl("w0eRkhR1z6A");

        Picture picture3 = new Picture();
        picture3.setPictureUrl("https://res.cloudinary.com/dedh1hh8k/image/upload/v1680880164/Star_Wars_Squadrons_spxqmv.jpg");
        picture3.setPublicId("Star_Wars_Squadrons_spxqmv");
        picture3.setTitle("Star Wars: Squadrons");
        picture3.setAuthor(userRepository.findUserByUsername("Admin").orElse(null));
        picture3.setGame(game3);

        gameRepository.save(game3);
        pictureRepository.save(picture3);


        Game game4 = new Game();
        game4.setApproved(null);
        game4.setDescription("Collect your favorite Star Wars characters, like Luke Skywalker, Han Solo, Darth Vader, and more, from every era – then conquer your opponents in epic, RPG-style combat. Build mighty teams and craft the best strategy to win battles across iconic locations to become the most legendary hologamer in the galaxy!\n" +
                "\n" +
                "Create Your Ultimate Team\n" +
                "\n" +
                "    Build powerful light and dark side teams with both Jedi and Sith heroes and other characters from the Star Wars universe. Make strategic choices and pick characters with complimentary abilities to construct squads and engage in RPG combat like never before!\n" +
                "\n" +
                "Collect Iconic Heroes\n" +
                "\n" +
                "    Collect characters from the original trilogy and prequel films, plus animated TV shows like Star Wars: The Clone Wars and Star Wars Rebels – and more. True to the RPG genre, each new hero has multiple powerful attacks and abilities!\n" +
                "\n" +
                "Train Powerful Champions\n" +
                "\n" +
                "    Make tactical decisions and equip your characters, from Darth Vader and Boba Fett – to Lando Calrissian and Leia Organa, with powerful gear to enhance their damage. Unlock special leader abilities to buff your team and unleash moves like Darth Sidious’s Force Lightning, Chewbacca’s Wookiee Rage, and more.\n" +
                "\n" +
                "Fight In Legendary Locations\n" +
                "\n" +
                "    Complete epic missions on Hoth, Bespin, Tatooine, Coruscant, and beyond. Unlock special characters to play through in light and dark side campaigns");
        game4.setReleaseDate(LocalDate.of(2015, 11, 24));
        game4.setTitle("Star Wars: Galaxy of Heroes");
        game4.setAuthor(userRepository.findUserByUsername("Admin").orElse(null));
        game4.setPlatform(platformRepository.findPlatformByName(PlatformNameEnum.MOBILE));
        game4.setVideoUrl("JSxYUZrwlvk");

        Picture picture4 = new Picture();
        picture4.setPictureUrl("https://res.cloudinary.com/dedh1hh8k/image/upload/v1680880164/Star_Wars_Galaxy_of_Heroes_wpzq70.jpg");
        picture4.setPublicId("Star_Wars_Galaxy_of_Heroes_wpzq70");
        picture4.setTitle("Star Wars: Galaxy of Heroes");
        picture4.setAuthor(userRepository.findUserByUsername("Admin").orElse(null));
        picture4.setGame(game4);

        gameRepository.save(game4);
        pictureRepository.save(picture4);


        Game game5 = new Game();
        game5.setApproved(null);
        game5.setDescription("The story of Cal Kestis continues in Star Wars Jedi: Survivor, a third person galaxy-spanning action-adventure game from Respawn Entertainment, developed in collaboration with Lucasfilm Games. This narratively-driven, single player title picks up five years after the events of Star Wars Jedi: Fallen Order and follows Cal’s increasingly desperate fight as the galaxy descends further into darkness.\n" +
                "\n" +
                "Stand against the darkness." +
                "\n" +
                "\n" +
                "Continue Cal’s Journey - No longer a Padawan, Cal has come into his own and grown into a powerful Jedi Knight. The Dark Times are closing in - with enemies new and familiar surrounding him, Cal will need to decide how far he’s willing to go to save those closest to him.\n" +
                "\n" +
                "Go Beyond Your Training - The cinematic combat system returns with additional Force abilities and new lightsaber fighting styles. Creatively leverage all these abilities and weapons to strategically take on an expanded host of enemies, sizing up strengths and weaknesses while cleverly utilizing your training to overcome your opponents and solve the mysteries that lay in your path.\n" +
                "\n" +
                "Explore an Untamed Galaxy - Discover new planets and familiar frontiers in the Star Wars galaxy, each with unique biomes, challenges, and enemies. Master new skills, equipment, and abilities that will augment the ways you explore, fight, and roam.\n");
        game5.setReleaseDate(LocalDate.of(2023, 4, 28));
        game5.setTitle("Star Wars Jedi: Survivor");
        game5.setAuthor(userRepository.findUserByUsername("Admin").orElse(null));
        game5.setPlatform(platformRepository.findPlatformByName(PlatformNameEnum.PC));
        game5.setVideoUrl("VRaobDJjiec?si");

        Picture picture5 = new Picture();
        picture5.setPictureUrl("https://res.cloudinary.com/dedh1hh8k/image/upload/v1693921400/qwrcosmgsfnlxbnzzr26.webp");
        picture5.setPublicId("qwrcosmgsfnlxbnzzr26");
        picture5.setTitle("Star Wars Jedi: Survivor");
        picture5.setAuthor(userRepository.findUserByUsername("Admin").orElse(null));
        picture5.setGame(game5);

        gameRepository.save(game5);
        pictureRepository.save(picture5);


        Game game6 = new Game();
        game6.setApproved(null);
        game6.setDescription("Immerse yourself in your STAR WARS™ battle fantasies. Feeling the ominous thud of an AT-AT stomping down on the frozen tundra of Hoth. Rebel forces firing blasters as Imperial speeder bikes zip through the lush forests of Endor. Intense dogfights between squadrons of X-wings and TIE fighters filling the skies. Immerse yourself in the epic STAR WARS™ battles you’ve always dreamed of and create new heroic moments of your own in STAR WARS™ Battlefront™.");
        game6.setReleaseDate(LocalDate.of(2015, 11, 19));
        game6.setTitle("Star Wars Battlefront");
        game6.setAuthor(userRepository.findUserByUsername("Admin").orElse(null));
        game6.setPlatform(platformRepository.findPlatformByName(PlatformNameEnum.PC));
        game6.setVideoUrl("V2xp-qtUlsQ?si");

        Picture picture6 = new Picture();
        picture6.setPictureUrl("https://res.cloudinary.com/dedh1hh8k/image/upload/v1692087549/vuzlhvo6mrn6hqafrzv0.jpg");
        picture6.setPublicId("vuzlhvo6mrn6hqafrzv0");
        picture6.setTitle("Star Wars Battlefront");
        picture6.setAuthor(userRepository.findUserByUsername("Admin").orElse(null));
        picture6.setGame(game6);

        gameRepository.save(game6);
        pictureRepository.save(picture6);


        Game game7 = new Game();
        game7.setApproved(null);
        game7.setDescription("Star Wars: The Old Republic is a free-to-play MMORPG that puts you at the center of your own story-driven saga. Play as a Jedi, Sith, Bounty Hunter, or one of many other iconic Star Wars roles in the galaxy far, far away over three thousand years before the classic films. The story of Legacy of the Sith builds upon Star Wars: The Old Republic’s dynamic plot and ongoing war between the Galactic Republic and the Sith Empire, allowing players to advance their level to a new cap of 80. Adventure to the aquatic planet of Manaan on a campaign to track down the insidious Darth Malgus - whose secret plans could spell doom for both the Jedi and the Sith. Legacy of the Sith brings new collaborative missions as well, including a 4-player Flashpoint set in a ruined Sith Fortress on the remote planet Elom.");
        game7.setReleaseDate(LocalDate.of(2011, 12, 20));
        game7.setTitle("Star Wars: The Old Republic");
        game7.setAuthor(userRepository.findUserByUsername("Admin").orElse(null));
        game7.setPlatform(platformRepository.findPlatformByName(PlatformNameEnum.PC));
        game7.setVideoUrl("YdgmH9Vv2-I?si");

        Picture picture7 = new Picture();
        picture7.setPictureUrl("https://res.cloudinary.com/dedh1hh8k/image/upload/v1692087023/rb01biianbhc4ikwpnsp.jpg");
        picture7.setPublicId("rb01biianbhc4ikwpnsp");
        picture7.setTitle("Star Wars: The Old Republic");
        picture7.setAuthor(userRepository.findUserByUsername("Admin").orElse(null));
        picture7.setGame(game7);

        gameRepository.save(game7);
        pictureRepository.save(picture7);


        Game game8 = new Game();
        game8.setApproved(null);
        game8.setDescription("\n" +
                "\n" +
                "The Star Wars Saga continues with Star Wars: The Force Unleashed II, the highly anticipated sequel to the fastest-selling Star Wars game ever created, which has sold more than seven million copies worldwide. In Star Wars: The Force Unleashed, the world was introduced to Darth Vader’s now fugitive apprentice, Starkiller — the unlikely hero who would ignite the flames of rebellion in a galaxy so desperately in need of a champion.\n" +
                "\n" +
                "In the sequel, Starkiller returns with over-the-top Force powers and embarks on a journey to discover his own identity and to reunite with his one true love, Juno Eclipse. In Star Wars: The Force Unleashed II, Starkiller is once again the pawn of Darth Vader — but instead of training his protégée as a ruthless assassin, the dark lord is attempting to clone his former apprentice in an attempt to create the Ultimate Sith warrior. The chase is on — Starkiller is in pursuit of Juno and Darth Vader is hunting for Starkiller.\n" +
                "\n" +
                "With all-new devastating Force powers and the ability to dual-wield lightsabers, Starkiller cuts a swath through deadly new enemies across exciting worlds from the Star Wars movies — all in his desperate search for answers to his past.\n");
        game8.setReleaseDate(LocalDate.of(2010, 10, 26));
        game8.setTitle("Star Wars: The Force Unleashed II");
        game8.setAuthor(userRepository.findUserByUsername("Admin").orElse(null));
        game8.setPlatform(platformRepository.findPlatformByName(PlatformNameEnum.PC));
        game8.setVideoUrl("ChVXYSU89fs?si");

        Picture picture8 = new Picture();
        picture8.setPictureUrl("https://res.cloudinary.com/dedh1hh8k/image/upload/v1692088971/ejdulhdsqbuta8kauz0i.jpg");
        picture8.setPublicId("ejdulhdsqbuta8kauz0i");
        picture8.setTitle("Star Wars: The Force Unleashed II");
        picture8.setAuthor(userRepository.findUserByUsername("Admin").orElse(null));
        picture8.setGame(game8);

        gameRepository.save(game8);
        pictureRepository.save(picture8);


        Game game9 = new Game();
        game9.setApproved(null);
        game9.setDescription("\n" +
                "\n" +
                "As the title implies, Star Wars: The Force Unleashed completely re-imagines the scope and scale of the Force to epic proportions. Players are cast into the role of Darth Vader’s secret apprentice, Starkiller, who has been trained to hunt down and destroy Jedi. During his quest, Starkiller will ally himself with a most unlikely set of heroes and be forced to make decisions that could change the course of his destiny and set events in motion that will forever shape the galaxy!\n" +
                "\n" +
                "By incorporating two ground-breaking technologies, Digital Molecular Matter (DMM) and euphoria, and paired with the powerful Havok physics system, players will interact with their environments and battle against enemies in new and exciting ways. Couple those technological advancements with devastating lightsaber attack combos, over-the-top Force powers like Force Grip and Force Repulse, and a storyline that takes place in the largely unexplored era between Star Wars: Episode III: Revenge of the Sith and Star Wars: Episode IV: A New Hope, Star Wars: The Force Unleashed provides a Star Wars gaming experience the likes of which have never been seen before.\n");
        game9.setReleaseDate(LocalDate.of(2008, 9, 16));
        game9.setTitle("Star Wars: The Force Unleashed");
        game9.setAuthor(userRepository.findUserByUsername("Admin").orElse(null));
        game9.setPlatform(platformRepository.findPlatformByName(PlatformNameEnum.PC));
        game9.setVideoUrl("49beQOa--Jc?si");

        Picture picture9 = new Picture();
        picture9.setPictureUrl("https://res.cloudinary.com/dedh1hh8k/image/upload/v1692088220/hmvdxkrmshgfdolfwqlr.jpg");
        picture9.setPublicId("hmvdxkrmshgfdolfwqlr");
        picture9.setTitle("Star Wars: The Force Unleashed");
        picture9.setAuthor(userRepository.findUserByUsername("Admin").orElse(null));
        picture9.setGame(game9);

        gameRepository.save(game9);
        pictureRepository.save(picture9);
    }
}
