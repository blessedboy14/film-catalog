package service.command;

/**
 * Enum for command names
 */
public enum CommandName {
        ABOUT("about"), START(""), NO_SUCH_COMMAND("no_such_command"), LIST_FILMS("films"),
        SIGN_IN("signin"), REGISTER("register"), LOG_OUT("logout"), ADD_FILM("add-film"),
        DETAIL("film"), REVIEW("review"), DELETE_FILM("delete-film"), CHANGE_LOCALE("locale");

    private String name;
    CommandName(String about) {
        this.name = about;
    }
    public String getName() {
        return name;
    }

    public static CommandName fromString(String text) {
        for (CommandName b : CommandName.values()) {
            if (b.name.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }
    @Override
    public String toString() {
        return name;
    }
}
