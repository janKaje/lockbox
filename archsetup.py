from os import rename, listdir, mkdir
from os.path import isfile, join

def customize(dir, substitutions):
    for file in listdir(dir):
        filename = join(dir, file)
        if isfile(filename):
            if file[-3:] == ".py":
                continue

            with open(filename, "r") as f:
                try:
                    contents = f.read()
                except Exception:
                    continue
            with open(filename, "w") as f:
                for (key, value) in substitutions:
                    contents = contents.replace(key, value)
                f.write(contents)
        else:
            customize(filename, substitutions)

        for (key, value) in substitutions:
            if key in file:
                new_name = file
                for (key, value) in substitutions:
                    new_name = new_name.replace(key, value)
                new_name = filename.replace(file, new_name)
                rename(filename, new_name)
                break


def substitutions(value):
    snake_case = value.lower().replace(" ", "_")
    pascal_case = value.title().replace(" ", "")
    kebab_case = value.lower().replace(" ", "-")
    lower_case = value.lower().replace(" ", "")
    title_case = value.title()
    expect_platform = pascal_case + "ExpectPlatform"
    return [
        ("    ", "  "),
        ("net.examplemod", "com.ssblur." + lower_case),
        ("ExampleExpectPlatform", expect_platform),
        ("Example Mod", title_case),
        ("ExampleMod", pascal_case),
        ("example_mod", snake_case),
        ("examplemod", lower_case),
        ("architectury-example-mod", kebab_case)
    ]

def main():
    print("Mod Name:")
    customize(".", substitutions(input()))
    for name in ("common", "forge", "fabric"):
        mkdir(f"./{name}/src/main/java/com")
        rename(f"./{name}/src/main/java/net", f"./{name}/src/main/java/com/ssblur")

if __name__ == "__main__":
    main()