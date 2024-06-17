import eyed3
from PIL import Image

def extract_album_art(mp3_file, output_image_file):
    audiofile = eyed3.load(mp3_file)
    if audiofile.tag:
        if audiofile.tag.images:
            for img in audiofile.tag.images:
                if img.mime_type == 'image/jpeg':  # Adjust mime type as per your requirement
                    with open(output_image_file, 'wb') as img_file:
                        img_file.write(img.image_data)
                    print(f"Album image saved to: {output_image_file}")
                    return True
        else:
            print("No album image found in the MP3 file.")
            return False
    else:
        print("No ID3 tag found in the MP3 file.")
        return False

# Example usage
mp3_file = '/Users/kimjeongil/Desktop/audio/팔레트 (Feat. G-DRAGON)_아이유_Palette.mp3'
output_image_file = '팔레트 (Feat. G-DRAGON)_아이유_Palette.jpg'
extract_album_art(mp3_file, output_image_file)
