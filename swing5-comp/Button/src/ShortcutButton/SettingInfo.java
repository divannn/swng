package ShortcutButton;
/**
 * @author idanilov
 *
 */
public enum SettingInfo {

    SETTING1("setting1", "Setting 1"), SETTING2("setting2", "Setting 2");

    private String id;
    private String title;

    private SettingInfo(String id) {
        this(id, id);
    }

    private SettingInfo(String id, String title) {
        this.id = id;
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

}
