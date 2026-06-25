import base64
import json
import urllib.request
import os

def main():
    if not os.path.exists("cli_menu_flow.mmd"):
        print("cli_menu_flow.mmd not found")
        return

    with open("cli_menu_flow.mmd", "r", encoding="utf-8") as f:
        diagram = f.read()

    state = {
        "code": diagram,
        "mermaid": "{\n  \"theme\": \"default\"\n}"
    }
    
    json_str = json.dumps(state)
    # The mermaid.ink API expects base64 string, sometimes without padding.
    encoded = base64.urlsafe_b64encode(json_str.encode('utf-8')).decode('ascii').rstrip("=")
    
    url = f"https://mermaid.ink/img/{encoded}"
    print(f"Fetching from: {url}")

    req = urllib.request.Request(url, headers={'User-Agent': 'Mozilla/5.0'})
    try:
        with urllib.request.urlopen(req) as response:
            with open("cli_menu_flow.png", "wb") as f:
                f.write(response.read())
        print("Successfully generated cli_menu_flow.png")
    except Exception as e:
        print(f"Error: {e}")

if __name__ == "__main__":
    main()
