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
                .map(game -> {
                    AllGamesViewModel currentGame = modelMapper.map(game, AllGamesViewModel.class);
                    Picture pictureByGameId = pictureRepository.findPictureByGame_Id(game.getId());
                    currentGame.setPicture(pictureByGameId);
                    return currentGame;
                }).collect(Collectors.toList());
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
    public List<AllGamesViewModel> findAllGamesWithValueNullOrFalse() {
        return gameRepository
                .findGamesThatAreNotApproved()
                .stream()
                .map(game -> {
                    AllGamesViewModel currentGame = modelMapper.map(game, AllGamesViewModel.class);
                    Picture pictureByGameId = pictureRepository.findPictureByGame_Id(game.getId());
                    currentGame.setPicture(pictureByGameId);
                    return currentGame;
                }).collect(Collectors.toList());
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
    }
}