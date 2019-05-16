package org.hds;

//调用示例：byte[] byteEncode = Encoding.UTF8.GetBytes(this.textBox1.Text);
//          this.textBox2.Text = new string(Base64Encoder.GetEncode(byteEncode));//Base64编码
public class Base64Encoder
{
    private static byte[] source;
    private static int length, length2;
    private static int blockCount;
    private static int paddingCount;

    public static char[] GetEncode(byte[] input)
    {
        source = input;
        length = input.length;
        if ((length % 3) == 0)
        {
            paddingCount = 0;
            blockCount = length / 3;
        }
        else
        {
            paddingCount = 3 - (length % 3);
            blockCount = (length + paddingCount) / 3;
        }
        length2 = length + paddingCount;


        byte[] source2;
        source2 = new byte[length2];

        for (int x = 0; x < length2; x++)
        {
            if (x < length)
            { source2[x] = source[x]; }
            else
            { source2[x] = 0; }
        }

        byte b1, b2, b3;
        byte temp, temp1, temp2, temp3, temp4;
        byte[] buffer = new byte[blockCount * 4];
        char[] result = new char[blockCount * 4];
        for (int x = 0; x < blockCount; x++)
        {
            b1 = source2[x * 3];
            b2 = source2[x * 3 + 1];
            b3 = source2[x * 3 + 2];

            temp1 = (byte)((b1 & 252) >> 2);

            temp = (byte)((b1 & 3) << 4);
            temp2 = (byte)((b2 & 240) >> 4);
            temp2 += temp;

            temp = (byte)((b2 & 15) << 2);
            temp3 = (byte)((b3 & 192) >> 6);
            temp3 += temp;

            temp4 = (byte)(b3 & 63);

            buffer[x * 4] = temp1;
            buffer[x * 4 + 1] = temp2;
            buffer[x * 4 + 2] = temp3;
            buffer[x * 4 + 3] = temp4;

        }

        for (int x = 0; x < blockCount * 4; x++)
        {
            result[x] = sixbit2char(buffer[x]);
        }


        switch (paddingCount)
        {
            case 0: break;
            case 1: result[blockCount * 4 - 1] = '='; break;
            case 2: result[blockCount * 4 - 1] = '=';
                result[blockCount * 4 - 2] = '=';
                break;
            default: break;
        }
        return result;
    }

    private static char sixbit2char(byte b)
    {
        char[] lookupTable = {'A','B','C','D','E','F','G','H','I','J','K','L','M',
                                           'N','O','P','Q','R','S','T','U','V','W','X','Y','Z',
                                           'a','b','c','d','e','f','g','h','i','j','k','l','m',
                                           'n','o','p','q','r','s','t','u','v','w','x','y','z',
                                           '0','1','2','3','4','5','6','7','8','9','+','/'};
        if ((b >= 0) && (b <= 63))
        { return lookupTable[(int)b]; }
        else
        { return ' '; }
    }

}
