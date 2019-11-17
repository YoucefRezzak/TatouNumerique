package tatou_numerique;

import java.util.Random;

public class WatermarkDet
{
    public boolean detectWatermarks(String[][] tuples, int secretKey)
    {
        Random generator = new Random();
        int nbrOfTuples = 3;
        int controlParameter = nbrOfTuples;
        int totalCount = 0;
        int matchCount = 0;

        for (int i = 0; i < nbrOfTuples; i++)
        {
            String primaryKey = tuples[i][0];
            generator.setSeed(Integer.parseInt(primaryKey + secretKey));

            if (generator.nextInt() % controlParameter == 0)   // tuple was marked.
            {
                int attributeIndex = generator.nextInt() % 4;  // attribute was marked.
                if (attributeIndex < 0) attributeIndex = -attributeIndex;
                int bitIndex = generator.nextInt() % 8;        // bit was marked.
                if (bitIndex < 0) bitIndex = -bitIndex;
                totalCount += 1;

                String attributeValue = tuples[i][attributeIndex];
                matchCount += match(generator.nextInt(), Integer.parseInt(attributeValue), bitIndex);
            }
        }

        // int significanceLevel = 0.5;
        // int threshold = totalCount / 2;
        // if ((matchCount < threshold) || (matchCount > totalCount - threshold)) return false;

        return true;
    }

    public int match (int randomNbr, int attributeValue, int bitIndex)
    {
        int result = 0;
        if (randomNbr % 2 == 0)
        {
            /*** if the attributeValue bit of index [bitIndex] is 0 then return 1, else return 0 */
            result = (((attributeValue >> bitIndex) & 1) == 0) ? 1 : 0;

        } else
        {
            /*** if the attributeValue bit of index [bitIndex] is 1 then return 1, else return 0 */
            result = (((attributeValue >> bitIndex) & 1) == 1) ? 1 : 0;
        }
        return result;
    }
}
