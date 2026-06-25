import re

def main():
    filepath = r"E:\Apps\Java Projects\DocuTrack\src\app\Main.java"
    with open(filepath, "r", encoding="utf-8") as f:
        content = f.read()

    # 1. Add static color variables and randomizer block
    static_block = """
    public static final String ANSI_RESET = "\\u001B[0m";
    public static final String[] COLORS = {
        "\\u001B[31m", "\\u001B[32m", "\\u001B[33m", "\\u001B[34m", "\\u001B[35m", "\\u001B[36m",
        "\\u001B[91m", "\\u001B[92m", "\\u001B[93m", "\\u001B[94m", "\\u001B[95m", "\\u001B[96m"
    };
    
    public static String logoColor;
    public static String borderColor;
    public static String titleColor;
    public static String optionColor;
    
    static {
        java.util.Random rand = new java.util.Random();
        logoColor = COLORS[rand.nextInt(COLORS.length)];
        do { borderColor = COLORS[rand.nextInt(COLORS.length)]; } while(borderColor.equals(logoColor));
        do { titleColor = COLORS[rand.nextInt(COLORS.length)]; } while(titleColor.equals(borderColor) || titleColor.equals(logoColor));
        do { optionColor = COLORS[rand.nextInt(COLORS.length)]; } while(optionColor.equals(titleColor) || optionColor.equals(borderColor));
    }
    
    public static void printMenuOption(String text) {
        System.out.println(optionColor + text + ANSI_RESET);
    }
"""
    
    # Insert just before 'static Scanner sc = new Scanner(System.in);'
    insert_idx = content.find("static Scanner sc = new Scanner(System.in);")
    if insert_idx != -1:
        content = content[:insert_idx] + static_block + "\n    " + content[insert_idx:]
    
    # 2. Update printHeader method
    old_print_header_pattern = re.compile(r'public static void printHeader\(String title\).*?^\s*\}', re.MULTILINE | re.DOTALL)
    
    new_print_header = """public static void printHeader(String title) {
        System.out.println("\\n");
        System.out.println(borderColor + "╔═══════════════════════════════════════════════════════════════════════════════════════╗" + ANSI_RESET);
        System.out.println(borderColor + "║ " + logoColor + "██████╗  ██████╗  ██████╗██╗   ██╗████████╗██████╗  █████╗  ██████╗██╗  ██╗" + borderColor + "           ║" + ANSI_RESET);
        System.out.println(borderColor + "║ " + logoColor + "██╔══██╗██╔═══██╗██╔════╝██║   ██║╚══██╔══╝██╔══██╗██╔══██╗██╔════╝██║ ██╔╝" + borderColor + "           ║" + ANSI_RESET);
        System.out.println(borderColor + "║ " + logoColor + "██║  ██║██║   ██║██║     ██║   ██║   ██║   ██████╔╝███████║██║     █████╔╝" + borderColor + "            ║" + ANSI_RESET);
        System.out.println(borderColor + "║ " + logoColor + "██║  ██║██║   ██║██║     ██║   ██║   ██║   ██╔══██╗██╔══██║██║     ██╔═██╗" + borderColor + "            ║" + ANSI_RESET);
        System.out.println(borderColor + "║ " + logoColor + "██████╔╝╚██████╔╝╚██████╗╚██████╔╝   ██║   ██║  ██║██║  ██║╚██████╗██║  ██╗" + borderColor + "           ║" + ANSI_RESET);
        System.out.println(borderColor + "║ " + logoColor + "╚═════╝  ╚═════╝  ╚═════╝ ╚═════╝    ╚═╝   ╚═╝  ╚═╝╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝" + borderColor + "           ║" + ANSI_RESET);
        System.out.println(borderColor + "╠═══════════════════════════════════════════════════════════════════════════════════════╣" + ANSI_RESET);
        int totalWidth = 87;
        int padding = (totalWidth - title.length()) / 2;
        int paddingRight = totalWidth - title.length() - padding;
        String format = borderColor + "║" + ANSI_RESET + "%" + (padding == 0 ? "" : padding) + "s" + titleColor + "%s" + ANSI_RESET + "%" + (paddingRight == 0 ? "" : paddingRight) + "s" + borderColor + "║\\n" + ANSI_RESET;
        System.out.printf(format, "", title, "");
        System.out.println(borderColor + "╚═══════════════════════════════════════════════════════════════════════════════════════╝" + ANSI_RESET);
    }"""
    
    content = old_print_header_pattern.sub(new_print_header, content)

    # 3. Replace menu options
    # Matches: System.out.println("  [1] Something");
    # Replace with: printMenuOption("  [1] Something");
    option_pattern = re.compile(r'System\.out\.println\("(\s*\[\d+\].*?)"\);')
    content = option_pattern.sub(r'printMenuOption("\1");', content)
    
    # 4. Prompt strings colorizer (e.g. Username:, Password:, Enter Choice:)
    # System.out.print("Username: "); -> System.out.print(optionColor + "Username: " + ANSI_RESET);
    prompt_pattern = re.compile(r'System\.out\.print\("(Enter Choice: |Username: |Password: |.*?: )"\);')
    content = prompt_pattern.sub(r'System.out.print(optionColor + "\1" + ANSI_RESET);', content)

    with open(filepath, "w", encoding="utf-8") as f:
        f.write(content)

if __name__ == "__main__":
    main()
