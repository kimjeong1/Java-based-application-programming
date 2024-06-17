import os
import eyed3

def extract_lyrics_from_folder(folder_path):
    for root, dirs, files in os.walk(folder_path):
        for file_name in files:
            if file_name.endswith(".mp3"):
                mp3_file = os.path.join(root, file_name)
                output_file = os.path.splitext(mp3_file)[0] + "_lyrics.txt"
                extract_lyrics(mp3_file, output_file)

def extract_lyrics(mp3_file, output_file):
    audiofile = eyed3.load(mp3_file)
    if audiofile.tag:
        lyrics = audiofile.tag.lyrics
        if lyrics:
            lyrics_text = str(lyrics[0].text).strip()  # 가사를 문자열로 변환하고 양쪽 공백을 제거합니다
            with open(output_file, 'w', encoding='utf-8') as f:
                f.write(lyrics_text)
            print(f"Lyrics saved to: {output_file}")
        else:
            print(f"No lyrics found in {mp3_file}.")
    else:
        print(f"No ID3 tag found in {mp3_file}.")

# Example usage: Extract lyrics from all MP3 files in a folder
folder_path = '/Users/kimjeongil/Desktop/audio'
extract_lyrics_from_folder(folder_path)
